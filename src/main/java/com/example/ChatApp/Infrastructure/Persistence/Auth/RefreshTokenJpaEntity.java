package com.example.ChatApp.Infrastructure.Persistence.Auth;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens",
        indexes = {
                @Index(name = "idx_refresh_token_token", columnList = "token"),
                @Index(name = "idx_refresh_token_user", columnList = "user_id")
        })
public class RefreshTokenJpaEntity {

    @Id
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false, unique = true, length = 512)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean revoked;

    protected RefreshTokenJpaEntity() {
        // for JPA
    }

    public static RefreshTokenJpaEntity create(
            String id,
            String userId,
            String token,
            Instant expiresAt,
            boolean revoked
    ) {
        RefreshTokenJpaEntity e = new RefreshTokenJpaEntity();
        e.id = id;
        e.userId = userId;
        e.token = token;
        e.expiresAt = expiresAt;
        e.revoked = revoked;
        return e;
    }

    // getters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getToken() { return token; }
    public Instant getExpiresAt() { return expiresAt; }
    public boolean isRevoked() { return revoked; }
}