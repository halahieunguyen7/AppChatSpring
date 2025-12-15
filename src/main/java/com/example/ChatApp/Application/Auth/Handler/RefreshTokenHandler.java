package com.example.ChatApp.Application.Auth.Handler;

import com.example.ChatApp.Application.Auth.Command.RefreshTokenCommand;
import com.example.ChatApp.Application.Auth.DTO.AuthTokenDTO;
import com.example.ChatApp.Domain.Auth.Exception.AuthDomainException;
import com.example.ChatApp.Domain.Auth.Model.RefreshToken;
import com.example.ChatApp.Domain.Auth.Repository.RefreshTokenRepository;
import com.example.ChatApp.Domain.Auth.Service.AccessToken;
import com.example.ChatApp.Domain.Auth.Service.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenHandler {
    private final RefreshTokenRepository repository;
    private final TokenGenerator tokenGenerator;

    public AuthTokenDTO handle(RefreshTokenCommand cmd) {

        RefreshToken token = repository.findByToken(cmd.refreshToken())
                .orElseThrow(() -> new AuthDomainException("Invalid refresh token"));

        AccessToken newAccessToken =
                tokenGenerator.generateAccessToken(token.getUserId());

        return new AuthTokenDTO(newAccessToken.value(), cmd.refreshToken(), newAccessToken.expiresAt());
    }
}
