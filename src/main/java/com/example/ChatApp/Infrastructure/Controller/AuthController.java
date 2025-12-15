package com.example.ChatApp.Infrastructure.Controller;

import com.example.ChatApp.Application.Auth.Command.LoginCommand;
import com.example.ChatApp.Application.Auth.Command.RefreshTokenCommand;
import com.example.ChatApp.Application.Auth.Command.RegisterUserCommand;
import com.example.ChatApp.Application.Auth.DTO.AuthTokenDTO;
import com.example.ChatApp.Application.Auth.Handler.LoginHandler;
import com.example.ChatApp.Application.Auth.Handler.RefreshTokenHandler;
import com.example.ChatApp.Application.Auth.Handler.RegisterUserHandler;
import com.example.ChatApp.Infrastructure.Controller.Request.Auth.LoginRequest;
import com.example.ChatApp.Infrastructure.Controller.Request.Auth.RefreshRequest;
import com.example.ChatApp.Infrastructure.Controller.Request.Auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterUserHandler registerUserHandler;
    private final LoginHandler loginHandler;
    private final RefreshTokenHandler refreshTokenHandler;

    @GetMapping("/")
    public String home() {
        return "Hello Fuck2!";
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest req) {
        registerUserHandler.handle(
                new RegisterUserCommand(
                        req.getEmail(), req.getPassword(), req.getFullName()
                )
        );
    }

    @PostMapping("/login")
    public AuthTokenDTO login(@RequestBody LoginRequest req) {
        return loginHandler.handle(
                new LoginCommand(req.getEmail(), req.getPassword())
        );
    }

    @PostMapping("/refresh")
    public AuthTokenDTO refresh(@RequestBody RefreshRequest req) {
        return refreshTokenHandler.handle(
                new RefreshTokenCommand(req.getRefreshToken())
        );
    }
}