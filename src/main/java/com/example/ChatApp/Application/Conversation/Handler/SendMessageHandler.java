package com.example.ChatApp.Application.Conversation.Handler;

import com.example.ChatApp.Application.Conversation.Command.SendMessageCommand;
import com.example.ChatApp.Domain.Conversation.Exception.ChatDomainException;
import com.example.ChatApp.Domain.Conversation.Model.Conversation;
import com.example.ChatApp.Domain.Conversation.Model.Message;
import com.example.ChatApp.Domain.Conversation.Model.MessageSummary;
import com.example.ChatApp.Domain.Conversation.Repository.ConversationRepository;
import com.example.ChatApp.Domain.Conversation.Repository.MessageRepository;
import com.example.ChatApp.Domain.Conversation.ValueObject.MessageContent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SendMessageHandler {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;

    @Transactional
    public void handle(SendMessageCommand cmd) {

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
    }
}
