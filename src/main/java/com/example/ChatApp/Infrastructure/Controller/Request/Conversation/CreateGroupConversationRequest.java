package com.example.ChatApp.Infrastructure.Controller.Request.Conversation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateGroupConversationRequest(
        @NotBlank String title,
        @NotEmpty List<String> userIds
) {}