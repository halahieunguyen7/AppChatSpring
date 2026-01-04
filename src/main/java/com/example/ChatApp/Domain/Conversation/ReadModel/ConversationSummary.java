package com.example.ChatApp.Domain.Conversation.ReadModel;

import java.time.Instant;

public record ConversationSummary(
        String id,
        String type,
        String title,
        String avatar,
        Instant lastMessageAt,
        String lastMessagePreview,
        Instant createdAt
) {
}