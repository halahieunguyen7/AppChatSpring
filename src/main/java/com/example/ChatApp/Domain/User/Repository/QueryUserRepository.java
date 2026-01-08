package com.example.ChatApp.Domain.User.Repository;

import com.example.ChatApp.Domain.User.ReadModel.UserSummary;

import java.util.Collection;
import java.util.Map;

public interface QueryUserRepository {
    UserSummary getSummary(String userId);

    Map<String, UserSummary> getSummaries(Collection<String> userIds);
}
