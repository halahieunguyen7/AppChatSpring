package com.example.ChatApp.Infrastructure.Redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PresenceService {

    private final StringRedisTemplate redis;

    public static final String KEY_PREFIX = "user:presence:";

    public void userOnline(String userId, String instanceId) {
        redis.opsForValue()
                .set(KEY_PREFIX + userId, instanceId);
    }

    public void userOffline(String userId) {
        redis.delete(KEY_PREFIX + userId);
    }

    public Map<String, String> getInstancesOfUsers(List<String> userIds) {
        Map<String, String> result = new HashMap<>();

        for (String userId : userIds) {
            String instanceId =
                    redis.opsForValue().get(KEY_PREFIX + userId);
            if (instanceId != null) {
                result.put(userId, instanceId);
            }
        }
        return result;
    }
}
