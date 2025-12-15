package com.example.ChatApp.Infrastructure.Mapper;

import com.example.ChatApp.Domain.Auth.Model.PasswordHash;
import com.example.ChatApp.Domain.Auth.Model.User;
import com.example.ChatApp.Domain.Auth.Model.UserId;
import com.example.ChatApp.Domain.Auth.ValueObject.Email;
import com.example.ChatApp.Infrastructure.Persistence.Auth.UserJpaEntity;

public final class UserMapper {

    private UserMapper() {}

    public static UserJpaEntity toEntity(User user) {
        return UserJpaEntity.create(
                user.getId().value(),
                user.getEmail().value(),
                user.getPassword().value(),
                user.getFullName(),
                user.getStatus()
        );
    }

    public static User toDomain(UserJpaEntity e) {
        return new User(
                UserId.of(e.getId()),
                new Email(e.getEmail()),
                new PasswordHash(e.getPasswordHash()),
                e.getFullName(),
                e.getStatus()
        );
    }
}