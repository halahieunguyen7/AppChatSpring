package com.example.ChatApp.Domain.Auth.ValueObject;

public record Email(String value) {

    public Email {
        if (!value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}