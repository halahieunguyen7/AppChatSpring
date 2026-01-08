package com.example.ChatApp.Domain.Conversation.ValueObject;

import com.example.ChatApp.Domain.GeneralService.UUIDv7;

import java.util.Objects;
import java.util.UUID;

public final class ConversationId {

    private final String value;

    private ConversationId(String value) {
        this.value = value;
    }

    public static ConversationId newId() {
        return new ConversationId(UUIDv7.generateUuidV7().toString());
    }

    public static ConversationId of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or blank");
        }
        return new ConversationId(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConversationId)) return false;
        ConversationId conversationId = (ConversationId) o;
        return value.equals(conversationId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
