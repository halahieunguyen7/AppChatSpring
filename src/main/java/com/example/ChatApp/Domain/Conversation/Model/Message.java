package com.example.ChatApp.Domain.Conversation.Model;

import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.MessageContent;
import com.example.ChatApp.Domain.Conversation.ValueObject.MessageId;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import lombok.Getter;

import java.time.Instant;

public class Message {

    @Getter
    private final MessageId id;
    @Getter
    private final ConversationId conversationId;
    @Getter
    private final UserId senderId;
    @Getter
    private final MessageContent content;
    @Getter
    private final Instant sentAt;

    private Message(MessageId id, ConversationId conversationId,UserId senderId, MessageContent content, Instant sentAt) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.sentAt = sentAt;
        this.conversationId = conversationId;
    }

    public static Message create(UserId senderId, ConversationId conversationId, MessageContent content) {
        return new Message(
                MessageId.newId(),
                conversationId,
                senderId,
                content,
                Instant.now()
        );
    }

    public static Message of(MessageId messageId, UserId senderId, ConversationId conversationId, MessageContent content, Instant sentAt) {
        return new Message(
                messageId,
                conversationId,
                senderId,
                content,
                sentAt
        );
    }
}