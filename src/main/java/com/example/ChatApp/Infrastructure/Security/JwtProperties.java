package com.example.ChatApp.Infrastructure.Security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private long accessTokenTtl;
    private long refreshTokenTtl;

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }

    public long getAccessTokenTtl() { return accessTokenTtl; }
    public void setAccessTokenTtl(long accessTokenTtl) {
        this.accessTokenTtl = accessTokenTtl;
    }

    public long getRefreshTokenTtl() { return refreshTokenTtl; }
    public void setRefreshTokenTtl(long refreshTokenTtl) {
        this.refreshTokenTtl = refreshTokenTtl;
    }
}