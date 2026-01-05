package com.example.ChatApp.Domain.ValueObject;

import com.example.ChatApp.Domain.Conversation.ReadModel.ConversationSummary;

import java.time.Instant;

public record CursorBase(
        String id
) {

    public static CursorBase from(String id) {
        return new CursorBase(
                id
        );
    }
}