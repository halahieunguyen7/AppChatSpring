package com.example.ChatApp.Infrastructure.Persistence.User;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserJpaRepository
        extends JpaRepository<UserJpaEntity, String> {

    @Override
    Optional<UserJpaEntity> findById(@NonNull String id);
}