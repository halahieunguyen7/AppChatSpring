package com.example.ChatApp.Domain.Auth.Model;

import java.util.Objects;

public final class PasswordHash {

    private final String value;

    public PasswordHash(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be null");
        }
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordHash that)) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "********";
    }
}