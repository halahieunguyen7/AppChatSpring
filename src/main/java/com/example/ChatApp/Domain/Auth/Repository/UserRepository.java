package com.example.ChatApp.Domain.Auth.Repository;

import com.example.ChatApp.Domain.Auth.Model.User;
import com.example.ChatApp.Domain.Auth.Model.UserId;
import com.example.ChatApp.Domain.Auth.ValueObject.Email;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findById(UserId id);

    Optional<User> findByEmail(Email email);

    boolean existsByEmail(Email email);
}