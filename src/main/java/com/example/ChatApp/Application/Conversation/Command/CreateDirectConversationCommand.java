package com.example.ChatApp.Application.Conversation.Command;

public record CreateDirectConversationCommand(
        String userId1,
        String userId2
) {}
