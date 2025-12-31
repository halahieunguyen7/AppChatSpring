package com.example.ChatApp.Domain.Conversation.ValueObject;

import com.example.ChatApp.Domain.Conversation.Exception.ChatDomainException;

@SuppressWarnings("ALL")
public record MessageContent(String value) {
    public MessageContent {
        if (value == null || value.isBlank()) {
            throw new ChatDomainException("Message cannot be empty");
        }
        if (value.length() > 2000) {
            throw new ChatDomainException("Message too long");
        }
    }

    @Override

    public String toString()
    {
        return value;
    }
}