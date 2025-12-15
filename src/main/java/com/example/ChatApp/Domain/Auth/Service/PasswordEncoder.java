package com.example.ChatApp.Domain.Auth.Service;

import com.example.ChatApp.Domain.Auth.Model.PasswordHash;

public interface PasswordEncoder {
    PasswordHash encode(String rawPassword);
    boolean matches(String rawPassword, PasswordHash hash);
}