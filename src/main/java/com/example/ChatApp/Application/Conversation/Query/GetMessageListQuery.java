package com.example.ChatApp.Application.Conversation.Query;

import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;

public record GetMessageListQuery(
        UserId userId,
        ConversationId conversationId,
        String cursor,
        int limit
) {
}