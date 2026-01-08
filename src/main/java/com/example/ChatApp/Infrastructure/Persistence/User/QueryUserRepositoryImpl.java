package com.example.ChatApp.Infrastructure.Persistence.User;

import com.example.ChatApp.Domain.User.ReadModel.UserSummary;
import com.example.ChatApp.Domain.User.Repository.QueryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryUserRepositoryImpl implements QueryUserRepository {
    private final UserJpaRepository userRepository;
    private final CacheManager cacheManager;

    @Override
    @Cacheable(
            cacheNames = "user-summaryy",
            key = "#userId",
            unless = "#result == null"
    )
    public UserSummary getSummary(String userId) {
        return userRepository.findById(userId)
                .map(this::toSummary)
                .orElse(null);
    }

    /* =========================
       Batch users (recommended)
       ========================= */
    @Override
    public Map<String, UserSummary> getSummaries(Collection<String> userIds) {

        if (userIds == null || userIds.isEmpty()) {
            return Map.of();
        }

        Cache cache = cacheManager.getCache("user-summaryy");

        Map<String, UserSummary> result = new HashMap<>();
        List<String> missIds = new ArrayList<>();

        // 1. Lấy từ cache trước
        for (String userId : userIds) {
            UserSummary cached = cache.get(userId, UserSummary.class);
            if (cached != null) {
                result.put(userId, cached);
            } else {
                missIds.add(userId);
            }
        }

        // 2. Query DB cho cache-miss
        if (!missIds.isEmpty()) {
            userRepository.findByIdIn(missIds)
                    .stream()
                    .map(this::toSummary)
                    .forEach(summary -> {
                        cache.put(summary.id(), summary); // cache lại
                        result.put(summary.id(), summary);
                    });
        }

        return result;
    }
    private UserSummary toSummary(UserJpaEntity e) {
        return new UserSummary(
                e.getId(),
                e.getFullName(),
                e.getAvatar()
        );
    }
}
