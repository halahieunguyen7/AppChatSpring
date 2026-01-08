package com.example.ChatApp.Domain.Conversation.ReadModel;

import java.time.Instant;

public record MessageSearchItem(
        String id,
        String conversationId,
        String senderId,
        String senderFullName,
        String senderAvatar,
        String message,
        Instant sentAt
) {}
