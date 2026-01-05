package com.example.ChatApp.Application.Conversation.DTO;

import com.example.ChatApp.Domain.Conversation.ReadModel.MessageSummary;

import java.time.Instant;

public record MessageItemDTO(
        String id,
        String senderId,
        String content,
        Instant sentAt
) {
    public static MessageItemDTO from(MessageSummary c) {
        return new MessageItemDTO(
                c.id(),
                c.senderId(),
                c.content(),
                c.sentAt()
        );
    }
}