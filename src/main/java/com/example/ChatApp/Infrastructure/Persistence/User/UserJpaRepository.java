package com.example.ChatApp.Infrastructure.Persistence.User;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserJpaRepository
        extends JpaRepository<UserJpaEntity, String> {

    @Override
    Optional<UserJpaEntity> findById(@NonNull String id);

    Optional<UserJpaEntity> findByEmail(@NonNull String email);

    List<UserJpaEntity> findByIdIn(Collection<String> ids);
}