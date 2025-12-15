package com.example.ChatApp.Application.Auth.Command;

public record LoginCommand(
        String email,
        String password
) {
}