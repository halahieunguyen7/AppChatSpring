package com.example.ChatApp.Infrastructure.Controller.Request.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UpdateUserRequest {
    @Getter
    private String fullName;

    @Getter
    private String avatarUrl;

    @Getter
    private int gender;
}
