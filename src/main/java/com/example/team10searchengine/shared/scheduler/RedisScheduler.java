package com.example.team10searchengine.shared.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisScheduler {
    private final RedisTemplate<String, String> redisTemplate;

    @Scheduled(cron = "0 0 */3 * * *")
    public void deleteAllRedis() {
        redisTemplate.delete("ranking");
    }
}
