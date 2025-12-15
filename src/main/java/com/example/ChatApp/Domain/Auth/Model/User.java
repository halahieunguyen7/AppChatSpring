package com.example.ChatApp.Domain.Auth.Model;

import com.example.ChatApp.Domain.Auth.Service.PasswordEncoder;
import com.example.ChatApp.Domain.Auth.ValueObject.Email;
import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter
    private final UserId id;
    @Getter
    private final Email email;
    @Getter
    private final PasswordHash password;
    @Getter
    private final String fullName;
    @Getter
    @Setter
    private UserStatus status;

    public User(UserId id, Email email, PasswordHash password, String fullName, UserStatus status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.status = status;
    }

    public boolean passwordMatches(String rawPassword, PasswordEncoder encoder) {
        return encoder.matches(rawPassword, password);
    }

}