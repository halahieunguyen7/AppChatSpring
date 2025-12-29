package com.example.ChatApp.Infrastructure.Controller.Request.Conversation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChatMessageRequest(

        @NotNull(message = "conversationId is required")
        String conversationId,

        @NotBlank(message = "content must not be blank")
        @Size(max = 5000, message = "content too long")
        String content

) {}