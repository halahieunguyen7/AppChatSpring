package com.example.ChatApp.Infrastructure.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RedisPresenceService {

    private final StringRedisTemplate redis;

    public Map<String, String> getInstancesOfUsers(List<String> userIds) {

        List<String> keys = userIds.stream()
                .map(id -> PresenceService.KEY_PREFIX + id)
                .toList();

        List<String> values = redis.opsForValue().multiGet(keys);

        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < userIds.size(); i++) {
            String instanceId = values.get(i);
            if (instanceId != null) {
                result.put(userIds.get(i), instanceId);
            }
        }
        return result;
    }
}
