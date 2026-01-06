package com.example.ChatApp.Application.Conversation.Command;

import java.util.List;

public record CreateGroupConversationCommand(
        String title,
        List<String> userIds,
        String creatorUserId
) {}
