package com.example.ChatApp.Domain.Auth.Model;

public enum UserStatus {

    ACTIVE,
    INACTIVE,
    SUSPENDED,
    DELETED;

    public boolean canLogin() {
        return this == ACTIVE;
    }
}