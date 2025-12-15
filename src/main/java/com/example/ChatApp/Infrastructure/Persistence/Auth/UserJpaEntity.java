package com.example.ChatApp.Infrastructure.Persistence.Auth;

import com.example.ChatApp.Domain.Auth.Model.UserStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserJpaEntity {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    protected UserJpaEntity() {
        // for JPA
    }

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public static UserJpaEntity create(
            String id,
            String email,
            String passwordHash,
            String fullName,
            UserStatus status
    ) {
        UserJpaEntity e = new UserJpaEntity();
        e.id = id;
        e.email = email;
        e.passwordHash = passwordHash;
        e.fullName = fullName;
        e.status = status;
        return e;
    }
}