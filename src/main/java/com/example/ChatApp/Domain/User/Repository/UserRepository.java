package com.example.ChatApp.Domain.User.Repository;


import com.example.ChatApp.Domain.User.Model.User;

public interface UserRepository {
    User findById(String id);

    void save(User user);
}
