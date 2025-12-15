package com.example.ChatApp.Domain.Conversation.ValueObject;

import com.example.ChatApp.Domain.Conversation.Exception.ChatDomainException;

public record Participant(UserId userId) {

    public Participant {
        if (userId == null) {
            throw new ChatDomainException("Participant userId is required");
        }
    }
}