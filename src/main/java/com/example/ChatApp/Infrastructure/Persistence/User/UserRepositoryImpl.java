package com.example.ChatApp.Infrastructure.Persistence.User;

import com.example.ChatApp.Domain.User.Exception.UserNotFoundException;
import com.example.ChatApp.Domain.User.Model.User;
import com.example.ChatApp.Domain.User.Repository.UserRepository;
import com.example.ChatApp.Infrastructure.Mapper.User.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User findById(String id) {
        User user = jpaRepository.findById(id)
                .map(UserMapper::toDomain).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public User findByEmail(String id) {
        User user = jpaRepository.findByEmail(id)
                .map(UserMapper::toDomain).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public void save(User user) {
        jpaRepository.save(UserMapper.toEntity(user));
    }
}
