package com.example.ChatApp.Application.Auth.Handler;

import com.example.ChatApp.Application.Auth.Command.RegisterUserCommand;
import com.example.ChatApp.Domain.Auth.Exception.AuthDomainException;
import com.example.ChatApp.Domain.Auth.Model.User;
import com.example.ChatApp.Domain.Auth.Model.UserId;
import com.example.ChatApp.Domain.Auth.Model.UserStatus;
import com.example.ChatApp.Domain.Auth.Repository.UserRepository;
import com.example.ChatApp.Domain.Auth.Service.PasswordEncoder;
import com.example.ChatApp.Domain.Auth.ValueObject.Email;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterUserHandler {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void handle(RegisterUserCommand cmd) {

        if (userRepository.existsByEmail(new Email(cmd.email()))) {
            throw new AuthDomainException("Email đã tồn tại tài khoản3");
        }

        User user = new User(
                UserId.newId(),
                new Email(cmd.email()),
                passwordEncoder.encode(cmd.password()),
                cmd.fullName(),
                UserStatus.ACTIVE
        );

        userRepository.save(user);
    }
}