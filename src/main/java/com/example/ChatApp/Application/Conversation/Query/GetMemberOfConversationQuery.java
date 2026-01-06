package com.example.ChatApp.Application.Conversation.Query;

import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;

public record GetMemberOfConversationQuery(
        String userId,
        String conversationId
) {
}