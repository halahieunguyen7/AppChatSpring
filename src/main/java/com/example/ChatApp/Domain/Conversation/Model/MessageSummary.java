package com.example.ChatApp.Domain.Conversation.Model;

import com.example.ChatApp.Domain.Conversation.ValueObject.MessageId;
import com.example.ChatApp.Domain.Conversation.ValueObject.UserId;

import java.time.Instant;

public record MessageSummary(
        MessageId messageId,
        UserId senderId,
        String preview,
        Instant sentAt
) {}
