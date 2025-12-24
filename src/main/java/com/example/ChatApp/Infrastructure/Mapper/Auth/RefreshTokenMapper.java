package com.example.ChatApp.Infrastructure.Mapper.Auth;

import com.example.ChatApp.Domain.Auth.Model.RefreshToken;
import com.example.ChatApp.Domain.Auth.Model.UserId;
import com.example.ChatApp.Infrastructure.Persistence.Auth.RefreshTokenJpaEntity;

public final class RefreshTokenMapper {

    private RefreshTokenMapper() {}

    public static RefreshTokenJpaEntity toEntity(RefreshToken token) {
        return RefreshTokenJpaEntity.create(
                token.getId(),
                token.getUserId().value(),
                token.getToken(),
                token.getExpiresAt(),
                token.isRevoked()
        );
    }

    public static RefreshToken toDomain(RefreshTokenJpaEntity e) {
        RefreshToken token =
                new RefreshToken(
                        UserId.of(e.getUserId()),
                        e.getToken(),
                        e.getExpiresAt()
                );
        if (e.isRevoked()) {
            token.revoke();
        }
        return token;
    }
}