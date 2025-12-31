package com.example.ChatApp.Application.Conversation.DTO;

public record ChatMessageResponse(
        String senderId,
        String content
) {}