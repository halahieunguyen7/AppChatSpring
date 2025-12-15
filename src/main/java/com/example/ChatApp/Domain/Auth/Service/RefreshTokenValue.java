package com.example.ChatApp.Domain.Auth.Service;

public record RefreshTokenValue(
        String value,
        long expiresAt
) {}