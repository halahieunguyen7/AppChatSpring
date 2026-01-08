package com.example.ChatApp.Application.User.Handler;

import com.example.ChatApp.Application.User.DTO.UserProfileDTO;
import com.example.ChatApp.Application.User.Query.GetCurrentUserQuery;
import com.example.ChatApp.Application.User.Query.GetUserByEmailQuery;
import com.example.ChatApp.Domain.User.Exception.UserNotFoundException;
import com.example.ChatApp.Domain.User.Model.User;
import com.example.ChatApp.Domain.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetUserByEmailHandle {

    private final UserRepository userRepository;

    public UserProfileDTO handle(GetUserByEmailQuery query) {
        User user;
        try {
            user = userRepository.findByEmail(query.email());
        } catch (UserNotFoundException e) {
            return null;
        }

        return new UserProfileDTO(
                user.getId().value(),
                user.getEmail().value(),
                user.getFullName(),
                user.getAvatar().value(),
                user.getGender().value
        );
    }
}
