package com.example.ChatApp.Domain.Auth.Repository;

import com.example.ChatApp.Domain.Auth.Model.RefreshToken;
import com.example.ChatApp.Domain.Auth.Model.UserId;

import java.util.Optional;

public interface RefreshTokenRepository {
    void save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(String token);

    void revokeByUserId(UserId userId);

    void delete(RefreshToken refreshToken);
}
