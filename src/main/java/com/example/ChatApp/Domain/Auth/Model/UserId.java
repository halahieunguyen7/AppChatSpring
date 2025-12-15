package com.example.ChatApp.Domain.Auth.Model;

import java.util.Objects;
import java.util.UUID;

public final class UserId {

    private final String value;

    private UserId(String value) {
        this.value = value;
    }

    public static UserId newId() {
        return new UserId(UUID.randomUUID().toString());
    }

    public static UserId of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or blank");
        }
        return new UserId(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;
        UserId userId = (UserId) o;
        return value.equals(userId.value);
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
