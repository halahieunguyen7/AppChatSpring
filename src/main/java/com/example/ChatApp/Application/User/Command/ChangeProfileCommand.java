package com.example.ChatApp.Application.User.Command;

import com.example.ChatApp.Domain.User.Model.Avatar;
import com.example.ChatApp.Domain.ValueObject.Gender;

public record ChangeProfileCommand(
        String userId,
        String fullName,
        Gender gender,
        Avatar avatar
) {
}