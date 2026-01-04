package com.example.ChatApp.Application.Conversation.DTO;

import com.example.ChatApp.Domain.Conversation.ReadModel.ConversationSummary;

import java.time.Instant;

public record ConversationItemDTO(
        String id,
        String type,
        String title,
        String avatar,
        Instant lastMessageAt,
        String lastMessagePreview,
        Instant createdAt
) {
    public static ConversationItemDTO from(ConversationSummary c) {
        return new ConversationItemDTO(
                c.id(),
                c.type(),
                c.title(),
                c.avatar(),
                c.lastMessageAt(),
                c.lastMessagePreview(),
                c.createdAt()
        );
    }
}