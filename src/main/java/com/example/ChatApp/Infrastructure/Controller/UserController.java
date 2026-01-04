package com.example.ChatApp.Infrastructure.Controller;

import com.example.ChatApp.Application.Auth.Command.RegisterUserCommand;
import com.example.ChatApp.Application.User.Command.ChangeProfileCommand;
import com.example.ChatApp.Application.User.DTO.UserProfileDTO;
import com.example.ChatApp.Application.User.Handler.ChangeProfileHandler;
import com.example.ChatApp.Application.User.Handler.GetCurrentUserHandler;
import com.example.ChatApp.Application.User.Query.GetCurrentUserQuery;
import com.example.ChatApp.Domain.Auth.Model.UserId;
import com.example.ChatApp.Domain.User.Model.Avatar;
import com.example.ChatApp.Domain.ValueObject.Gender;
import com.example.ChatApp.Infrastructure.Controller.Request.Auth.RegisterRequest;
import com.example.ChatApp.Infrastructure.Controller.Request.User.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final ChangeProfileHandler changeProfileHandler;
    private final GetCurrentUserHandler getCurrentUserHandler;

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

    @GetMapping("/me")
    public UserProfileDTO me(Principal principal) {
        return getCurrentUserHandler.handle(
                new GetCurrentUserQuery(
                        UserId.of(principal.getName())
                )
        );
    }
}
