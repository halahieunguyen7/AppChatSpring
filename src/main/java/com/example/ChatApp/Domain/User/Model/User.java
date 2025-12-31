package com.example.ChatApp.Domain.User.Model;

import com.example.ChatApp.Domain.Auth.Model.UserId;
import com.example.ChatApp.Domain.Auth.ValueObject.Email;
import com.example.ChatApp.Domain.ValueObject.Gender;
import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter
    private final UserId id;

    @Getter
    private final Email email;

    @Setter
    @Getter
    private String fullName;

    @Getter
    @Setter
    private Avatar avatar;

    @Getter
    @Setter
    private Gender gender;


    public User(UserId id, Email email, String fullName, Avatar avatar, Gender gender) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.avatar = avatar;
        this.gender = gender;
    }


}