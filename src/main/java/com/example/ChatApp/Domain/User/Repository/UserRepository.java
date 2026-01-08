package com.example.ChatApp.Domain.User.Repository;


import com.example.ChatApp.Domain.User.Model.User;

public interface UserRepository {
    User findById(String id);
    User findByEmail(String email);

    void save(User user);
}
