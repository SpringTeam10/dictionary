package com.example.team10searchengine.shared.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisScheduler {
    private final RedisTemplate<String, String> redisTemplate;

    @Scheduled(cron = "0 0 */1 * * *")
    public void deleteAllRedis() {
        redisTemplate.delete("korranking");
        redisTemplate.delete("ranking");
        log.info("Redis 삭제 스케줄러 완료");
    }
}
