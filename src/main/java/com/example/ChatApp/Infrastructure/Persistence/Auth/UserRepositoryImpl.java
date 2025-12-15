package com.example.ChatApp.Infrastructure.Persistence.Auth;

import com.example.ChatApp.Domain.Auth.Model.User;
import com.example.ChatApp.Domain.Auth.Model.UserId;
import com.example.ChatApp.Domain.Auth.Repository.UserRepository;
import com.example.ChatApp.Domain.Auth.ValueObject.Email;
import com.example.ChatApp.Infrastructure.Mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(User user) {
        jpaRepository.save(UserMapper.toEntity(user));
    }

    @Override
    public Optional<User> findById(UserId id) {
        return jpaRepository.findById(id.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return jpaRepository.existsByEmail(email.value());
    }
}