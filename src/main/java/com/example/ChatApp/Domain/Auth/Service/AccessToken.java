package com.example.ChatApp.Domain.Auth.Service;

public record AccessToken(
        String value,
        long expiresAt
) {}