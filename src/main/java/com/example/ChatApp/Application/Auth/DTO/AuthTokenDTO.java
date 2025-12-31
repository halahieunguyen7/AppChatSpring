package com.example.ChatApp.Application.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthTokenDTO {
    private final String accessToken;
    private final String refreshToken;
    private final long accessTokenExpiresAt;
}