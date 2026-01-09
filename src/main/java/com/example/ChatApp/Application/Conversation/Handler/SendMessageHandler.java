package com.example.ChatApp.Application.Conversation.Handler;

import com.example.ChatApp.Application.Conversation.Command.SendMessageCommand;
import com.example.ChatApp.Application.Conversation.DTO.ChatMessageResponse;
import com.example.ChatApp.Domain.Conversation.Event.MessageSentEvent;
import com.example.ChatApp.Domain.Conversation.Exception.ChatDomainException;
import com.example.ChatApp.Domain.Conversation.Model.Conversation;
import com.example.ChatApp.Domain.Conversation.Model.Message;
import com.example.ChatApp.Domain.Conversation.Model.MessageSummary;
import com.example.ChatApp.Domain.Conversation.Repository.ConversationRepository;
import com.example.ChatApp.Domain.Conversation.Repository.MessageRepository;
import com.example.ChatApp.Domain.Conversation.ValueObject.MessageContent;
import com.example.ChatApp.Domain.GeneralService.UUIDv7;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SendMessageHandler {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final KafkaTemplate<String, MessageSentEvent> kafka;
    private final SimpMessagingTemplate messagingTemplate;

    public void handle(SendMessageCommand cmd) {

        Conversation conversation = conversationRepository
                .findById(cmd.conversationId())
                .orElseThrow(() -> new ChatDomainException("Không tìm thấy cuộc trò chuyện"));

        conversation.validateSender(cmd.senderId());

        MessageSentEvent event = new MessageSentEvent(
                UUIDv7.generateUuidV7().toString(),
                cmd.conversationId().value(),
                cmd.senderId().value(),
                cmd.content(),
                Instant.now()
        );

        kafka.send("chat-message-events", event);
    }

    @Transactional
    public void handleDeprecate(SendMessageCommand cmd) {

        Optional<Conversation> conversationO =
                conversationRepository.findById(cmd.conversationId());
        if (conversationO.isEmpty()) {
            throw new ChatDomainException("Không tim thấy cuộc trò chuyện");
        }
        Conversation conversation = conversationO.get();

        conversation.validateSender(cmd.senderId());

        Message message = Message.create(
                cmd.senderId(),
                cmd.conversationId(),
                new MessageContent(cmd.content())
        );

        messageRepository.save(message);

        conversation.updateLastMessage(
                new MessageSummary(
                        message.getId(),
                        cmd.senderId(),
                        message.getContent().value(),
                        message.getSentAt()
                )
        );

        conversationRepository.save(conversation);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + cmd.conversationId(),
                new ChatMessageResponse(
                        message.getId().value(),
                        cmd.senderId().toString(),
                        cmd.content(),
                        message.getSentAt()
                )
        );
    }
}
