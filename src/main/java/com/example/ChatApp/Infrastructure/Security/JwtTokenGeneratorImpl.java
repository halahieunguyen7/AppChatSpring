package com.example.ChatApp.Infrastructure.Security;

import com.example.ChatApp.Domain.Auth.Model.User;
import com.example.ChatApp.Domain.Auth.Model.UserId;
import com.example.ChatApp.Domain.Auth.Service.AccessToken;
import com.example.ChatApp.Domain.Auth.Service.RefreshTokenValue;
import com.example.ChatApp.Domain.Auth.Service.TokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenGeneratorImpl implements TokenGenerator {
    private final JwtProperties props;
    private final Key secretKey;

    public JwtTokenGeneratorImpl(JwtProperties props) {
        this.props = props;
        this.secretKey =
                Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public AccessToken generateAccessToken(UserId userId) {

        long expiresAt =
                System.currentTimeMillis() + props.getAccessTokenTtl();

        String token = Jwts.builder()
                .setSubject(userId.value())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiresAt))
                .signWith(secretKey)
                .compact();

        return new AccessToken(token, expiresAt);
    }

    @Override
    public RefreshTokenValue generateRefreshToken(User user) {

        long expiresAt =
                System.currentTimeMillis() + props.getRefreshTokenTtl();

        String token = Jwts.builder()
                .setSubject(user.getId().value())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiresAt))
                .signWith(secretKey)
                .compact();

        return new RefreshTokenValue(token, expiresAt);
    }
}
