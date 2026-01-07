package com.example.ChatApp.Domain.Conversation.Event;

import java.time.Instant;

public record MessageSentEvent(
        String id,
        String conversationId,
        String senderId,
        String content,
        Instant sentAt
) {}