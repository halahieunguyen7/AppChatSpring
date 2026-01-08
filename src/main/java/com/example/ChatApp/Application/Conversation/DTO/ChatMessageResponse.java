package com.example.ChatApp.Application.Conversation.DTO;

import java.time.Instant;

public record ChatMessageResponse(
        String id,
        String senderId,
        String content,
        Instant sentAt
) {}