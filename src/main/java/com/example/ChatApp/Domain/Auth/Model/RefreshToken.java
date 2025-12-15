package com.example.ChatApp.Domain.Auth.Model;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken {

    private final String id;
    private final UserId userId;
    private final String token;
    private final Instant expiresAt;
    private boolean revoked;

    public RefreshToken(UserId userId, String token, Instant expiresAt) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
        this.revoked = false;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public void revoke() {
        this.revoked = true;
    }

    public String getId() { return id; }
    public UserId getUserId() { return userId; }
    public String getToken() { return token; }
    public Instant getExpiresAt() { return expiresAt; }
    public boolean isRevoked() { return revoked; }
}