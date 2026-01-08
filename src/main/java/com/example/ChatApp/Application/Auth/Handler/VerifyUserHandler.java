package com.example.ChatApp.Application.Auth.Handler;

import com.example.ChatApp.Application.Auth.Command.VerifyUserCommand;
import com.example.ChatApp.Domain.Auth.Exception.AuthDomainException;
import com.example.ChatApp.Domain.Auth.Model.User;
import com.example.ChatApp.Domain.Auth.Model.UserId;
import com.example.ChatApp.Domain.Auth.Model.UserStatus;
import com.example.ChatApp.Domain.Auth.Repository.UserRepository;
import com.example.ChatApp.Infrastructure.Security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyUserHandler {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtProvider;

    public void handle(VerifyUserCommand cmd) {
        String userId = "";
        Claims claims = jwtProvider.parseToken(cmd.token());
        userId = claims.getSubject();

        if (userId == null || userId.isEmpty()) {
            throw new AuthDomainException("Invalid token");
        }

        User user = userRepository.findById(UserId.of(userId)).orElse(null);

        if (user == null || user.getStatus() != UserStatus.NOT_VERIFY) {
            throw new AuthDomainException("Invalid token");
        }

        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);
    }
}
