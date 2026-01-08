package com.example.ChatApp.Domain.Conversation.ReadModel;

import com.example.ChatApp.Domain.ValueObject.CursorBase;

import java.util.List;

public record MessageSearchResponse(
        List<MessageSearchItem> items,
        String nextCursor
) {
    public static MessageSearchResponse empty() {
        return new MessageSearchResponse(List.of(), null);
    }
}
