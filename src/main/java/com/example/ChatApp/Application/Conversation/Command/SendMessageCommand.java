package com.example.ChatApp.Application.Conversation.Command;

import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;

public record SendMessageCommand(
        ConversationId conversationId,
        UserId senderId,
        String content
) {
    public SendMessageCommand {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Message content must not be empty");
        }
    }
}
