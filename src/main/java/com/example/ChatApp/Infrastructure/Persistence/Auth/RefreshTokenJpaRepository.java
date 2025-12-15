package com.example.ChatApp.Infrastructure.Persistence.Auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepository
        extends JpaRepository<RefreshTokenJpaEntity, String> {

    Optional<RefreshTokenJpaEntity> findByToken(String token);

    void deleteByUserId(String userId);
}