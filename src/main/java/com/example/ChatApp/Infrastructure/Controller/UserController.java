package com.example.ChatApp.Infrastructure.Controller;

import com.example.ChatApp.Application.Auth.Command.RegisterUserCommand;
import com.example.ChatApp.Application.User.Command.ChangeProfileCommand;
import com.example.ChatApp.Application.User.Handler.ChangeProfileHandler;
import com.example.ChatApp.Domain.User.Model.Avatar;
import com.example.ChatApp.Domain.ValueObject.Gender;
import com.example.ChatApp.Infrastructure.Controller.Request.Auth.RegisterRequest;
import com.example.ChatApp.Infrastructure.Controller.Request.User.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final ChangeProfileHandler changeProfileHandler;

    @PostMapping("/update")
    public void update(@RequestBody UpdateUserRequest req, Principal principal) {

        changeProfileHandler.handle(
                new ChangeProfileCommand(
                    principal.getName(),
                    req.getFullName(),
                    Gender.tryFrom(req.getGender()),
                    new Avatar(req.getAvatarUrl())
                )
        );
    }
}
