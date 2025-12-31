package com.example.ChatApp.Infrastructure.Persistence.Auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserJpaRepository
        extends JpaRepository<AuthUserJpaEntity, String> {

    Optional<AuthUserJpaEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}