package com.example.ChatApp.Domain.Conversation.Model;

import com.example.ChatApp.Domain.Conversation.ValueObject.MessageContent;
import com.example.ChatApp.Domain.Conversation.ValueObject.MessageId;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;
import lombok.Getter;

import java.time.Instant;

public class Message {

    @Getter
    private final MessageId id;
    @Getter
    private final UserId senderId;
    @Getter
    private final MessageContent content;
    @Getter
    private final Instant sentAt;

    private Message(MessageId id, UserId senderId, MessageContent content, Instant sentAt) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.sentAt = sentAt;
    }

    public static Message create(UserId senderId, MessageContent content) {
        return new Message(
                MessageId.newId(),
                senderId,
                content,
                Instant.now()
        );
    }
}