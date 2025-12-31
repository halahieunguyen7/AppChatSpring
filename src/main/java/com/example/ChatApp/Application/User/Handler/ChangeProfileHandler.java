package com.example.ChatApp.Application.User.Handler;

import com.example.ChatApp.Application.User.Command.ChangeProfileCommand;
import com.example.ChatApp.Domain.User.Model.User;
import com.example.ChatApp.Domain.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeProfileHandler {
    private final UserRepository userRepository;

    public User handle(ChangeProfileCommand command) {
        User user = userRepository.findById(command.userId());

        if (command.fullName() != null) {
            user.setFullName(command.fullName());
        }

        if (command.gender() != null) {
            user.setGender(command.gender());
        }

        if (command.avatar() != null) {
            user.setAvatar(command.avatar());
        }

        userRepository.save(user);

        return user;
    }
}
