package com.example.ChatApp.Application.User.Handler;

import com.example.ChatApp.Application.User.DTO.UserProfileDTO;
import com.example.ChatApp.Application.User.Query.GetCurrentUserQuery;
import com.example.ChatApp.Domain.User.Model.User;
import com.example.ChatApp.Domain.User.Repository.UserRepository;
import com.example.ChatApp.Infrastructure.Persistence.ReadOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@ReadOnly
public class GetCurrentUserHandler {

    private final UserRepository userRepository;

    public UserProfileDTO handle(GetCurrentUserQuery query) {
        User user = userRepository.findById(query.userId().value());

        return new UserProfileDTO(
                user.getId().value(),
                user.getEmail().value(),
                user.getFullName(),
                user.getAvatar().value(),
                user.getGender().value
        );
    }
}
