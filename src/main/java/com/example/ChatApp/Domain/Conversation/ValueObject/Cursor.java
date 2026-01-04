package com.example.ChatApp.Domain.Conversation.ValueObject;

import com.example.ChatApp.Domain.Conversation.ReadModel.ConversationSummary;

import java.time.Instant;

public record Cursor(
        Instant lastMessageAt,
        String conversationId
) {

    public static Cursor from(ConversationSummary summary) {
        return new Cursor(
                summary.lastMessageAt(),
                summary.id()
        );
    }
}