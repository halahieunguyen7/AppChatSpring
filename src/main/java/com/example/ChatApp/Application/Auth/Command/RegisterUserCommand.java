package com.example.ChatApp.Application.Auth.Command;

public record RegisterUserCommand(
        String email,
        String password,
        String fullName
) {
}