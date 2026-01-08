package com.example.ChatApp.Application.Conversation.Query;

public record SearchMessageQuery(
        String conversationId,
        String keyword,
        String userId,
        String cursor
) {
}