package com.example.ChatApp.Infrastructure.Consumer;

import com.example.ChatApp.Application.Conversation.DTO.ChatMessageResponse;
import com.example.ChatApp.Domain.Conversation.Event.MessageSentEvent;
import com.example.ChatApp.Domain.Conversation.Model.Conversation;
import com.example.ChatApp.Domain.Conversation.Model.Message;
import com.example.ChatApp.Domain.Conversation.Model.MessageSummary;
import com.example.ChatApp.Domain.Conversation.Repository.ConversationQueryRepository;
import com.example.ChatApp.Domain.Conversation.Repository.ConversationRepository;
import com.example.ChatApp.Domain.Conversation.Repository.MessageRepository;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.MessageContent;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import com.example.ChatApp.Infrastructure.Redis.RedisPresenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatProcessor {

    private final ConversationRepository conversationRepository;
    private final ConversationQueryRepository conversationQueryRepository;
    private final MessageRepository messageRepository;
    private final RedisPresenceService presenceService;
    private final RedisTemplate<String, Object> redisTemplate;

    @KafkaListener(
            topics = "chat-message-events",
            groupId = "chat-core",
            containerFactory = "chatKafkaListenerContainerFactory"
    )
    @Transactional
    public void onMessage(MessageSentEvent e) {

        Conversation conversation = conversationRepository
                .findById(ConversationId.of(e.conversationId()))
                .orElseThrow();

        Message message = Message.create(
                UserId.of(e.senderId()),
                ConversationId.of(e.conversationId()),
                new MessageContent(e.content())
        );

        messageRepository.save(message);

        conversation.updateLastMessage(
                new MessageSummary(
                        message.getId(),
                        message.getSenderId(),
                        message.getContent().value(),
                        message.getSentAt()
                )
        );

        conversationRepository.save(conversation);

        fanoutToOnlineUsers(conversation, message);
    }

    private void fanoutToOnlineUsers(
            Conversation conversation,
            Message message
    ) {
        List<String> memberIds = conversationQueryRepository.getMemberIdsOfConversations(conversation.getId().value())
                .stream()
                .filter(id -> !id.equals(message.getSenderId().toString()))
                .toList();
        Map<String, String> onlineUsers =
                presenceService.getInstancesOfUsers(memberIds);

        ChatMessageResponse payload =
                new ChatMessageResponse(
                        message.getId().value(),
                        message.getSenderId().toString(),
                        message.getContent().value(),
                        message.getSentAt()
                );

        onlineUsers.forEach((userId, instanceId) -> {
            WsEvent event = new WsEvent(
                    instanceId,
                    userId,
                    payload
            );
            redisTemplate.convertAndSend("ws-events", event);
        });
    }
}