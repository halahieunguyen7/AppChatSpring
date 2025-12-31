package com.example.ChatApp.Infrastructure.Mapper.User;

import com.example.ChatApp.Domain.Auth.Model.UserId;
import com.example.ChatApp.Domain.Auth.ValueObject.Email;
import com.example.ChatApp.Domain.User.Model.Avatar;
import com.example.ChatApp.Domain.User.Model.User;
import com.example.ChatApp.Domain.ValueObject.Gender;
import com.example.ChatApp.Infrastructure.Persistence.User.UserJpaEntity;

public class UserMapper {
    private UserMapper() {}

    public static UserJpaEntity toEntity(User user) {
        return UserJpaEntity.build(
                user.getId().value(),
                user.getEmail().value(),
                user.getFullName(),
                user.getAvatar().value(),
                user.getGender().value
        );
    }

    public static User toDomain(UserJpaEntity e) {
        return new User(
                UserId.of(e.getId()),
                new Email(e.getEmail()),
                e.getFullName(),
                new Avatar(e.getAvatar()),
                Gender.tryFrom(e.getGender())
        );
    }
}
