package com.example.ChatApp.Application.Conversation.Query;

import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;

public record GetConversationListQuery(
        UserId userId,
        String cursor,
        int limit
) {
}