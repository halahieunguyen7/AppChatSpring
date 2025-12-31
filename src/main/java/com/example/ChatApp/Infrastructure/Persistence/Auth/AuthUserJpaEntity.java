package com.example.ChatApp.Infrastructure.Persistence.Auth;

import com.example.ChatApp.Domain.Auth.Model.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class AuthUserJpaEntity {

    // getters & setters
    @lombok.Setter
    @Id
    private String id;

    @lombok.Setter
    @Column(nullable = false, unique = true)
    private String email;

    @lombok.Setter
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @lombok.Setter
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    protected AuthUserJpaEntity() {
        // for JPA
    }

    public static AuthUserJpaEntity create(
            String id,
            String email,
            String passwordHash,
            String fullName,
            UserStatus status
    ) {
        AuthUserJpaEntity e = new AuthUserJpaEntity();
        e.id = id;
        e.email = email;
        e.passwordHash = passwordHash;
        e.fullName = fullName;
        e.status = status;
        return e;
    }
}