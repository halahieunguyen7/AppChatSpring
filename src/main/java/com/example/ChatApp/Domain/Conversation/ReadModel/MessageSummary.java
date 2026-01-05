package com.example.ChatApp.Domain.Conversation.ReadModel;

import java.time.Instant;

public record MessageSummary(
        String id,
        String senderId,
        String content,
        Instant sentAt
) {
}