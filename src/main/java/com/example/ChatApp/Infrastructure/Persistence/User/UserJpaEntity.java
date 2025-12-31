package com.example.ChatApp.Infrastructure.Persistence.User;

import com.example.ChatApp.Domain.User.Model.Avatar;
import com.example.ChatApp.Domain.ValueObject.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "users")
public class UserJpaEntity {

    // getters & setters
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Setter
    private String avatar;

    @Setter
    private int gender;


    protected UserJpaEntity() {
        // for JPA
    }



    public static UserJpaEntity build(
            String id,
            String email,
            String fullName,
            String avatar,
            int gender
    ) {
        UserJpaEntity e = new UserJpaEntity();
        e.id = id;
        e.email = email;
        e.fullName = fullName;
        e.avatar = avatar;
        e.gender = gender;

        return e;
    }
}