package com.example.ChatApp.Domain.Auth.Service;

import com.example.ChatApp.Domain.Auth.Model.User;
import com.example.ChatApp.Domain.Auth.Model.UserId;

public interface TokenGenerator {
    AccessToken generateAccessToken(UserId userId);
    RefreshTokenValue generateRefreshToken(User user);
}