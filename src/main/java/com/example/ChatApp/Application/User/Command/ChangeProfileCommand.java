package com.example.ChatApp.Application.User.Command;

import com.example.ChatApp.Domain.User.Avatar;
import com.example.ChatApp.Domain.ValueObject.Gender;

public record ChangeProfileCommand(
        String fullName,
        Gender gender,
        Avatar avatarUrl
) {
}