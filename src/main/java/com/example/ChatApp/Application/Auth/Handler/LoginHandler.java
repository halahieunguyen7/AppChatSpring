package com.example.ChatApp.Application.Auth.Handler;

import com.example.ChatApp.Application.Auth.Command.LoginCommand;
import com.example.ChatApp.Application.Auth.DTO.AuthTokenDTO;
import com.example.ChatApp.Domain.Auth.Exception.AuthDomainException;
import com.example.ChatApp.Domain.Auth.Model.RefreshToken;
import com.example.ChatApp.Domain.Auth.Model.User;
import com.example.ChatApp.Domain.Auth.Model.UserStatus;
import com.example.ChatApp.Domain.Auth.Repository.RefreshTokenRepository;
import com.example.ChatApp.Domain.Auth.Repository.UserRepository;
import com.example.ChatApp.Domain.Auth.Service.AccessToken;
import com.example.ChatApp.Domain.Auth.Service.PasswordEncoder;
import com.example.ChatApp.Domain.Auth.Service.RefreshTokenValue;
import com.example.ChatApp.Domain.Auth.Service.TokenGenerator;
import com.example.ChatApp.Domain.Auth.ValueObject.Email;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginHandler {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthTokenDTO handle(LoginCommand cmd) {

        User user = userRepository.findByEmail(new Email(cmd.email()))
                .orElseThrow(() -> new AuthDomainException("Tài khoản và mật khẩu không hợp lệ"));

        if (!user.passwordMatches(cmd.password(), passwordEncoder)) {
            throw new AuthDomainException("Tài khoản và mật khẩu không hợp lệ");
        }

        if (user.getStatus() == UserStatus.NOT_VERIFY) {
            throw new AuthDomainException("Tài khoản chưa xác thực, vui lòng xác thực email");
        }

        AccessToken accessToken = tokenGenerator.generateAccessToken(user.getId());
        RefreshTokenValue refreshToken = tokenGenerator.generateRefreshToken(user);

        refreshTokenRepository.save(
                new RefreshToken(user.getId(), refreshToken.value(), Instant.ofEpochMilli(refreshToken.expiresAt()))
        );

        return new AuthTokenDTO(accessToken.value(), refreshToken.value(), accessToken.expiresAt());
    }
}