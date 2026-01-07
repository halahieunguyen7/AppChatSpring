package com.example.ChatApp.Application.User.DTO;

import com.example.ChatApp.Domain.ValueObject.Gender;

public record UserProfileDTO(
        String id,
        String email,
        String fullName,
        String avatar,
        int gender
) {
}