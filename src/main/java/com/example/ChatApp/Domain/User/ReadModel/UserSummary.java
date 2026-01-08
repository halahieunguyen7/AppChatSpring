package com.example.ChatApp.Domain.User.ReadModel;

import java.io.Serializable;

public record UserSummary(
        String id,
        String fullName,
        String avatar
) implements Serializable {}

