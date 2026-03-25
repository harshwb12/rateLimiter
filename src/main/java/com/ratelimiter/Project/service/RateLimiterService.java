package com.ratelimiter.Project.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;

    private DefaultRedisScript<Long> redisScript;

    @PostConstruct
    public void loadScript() {
        redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("scripts/rate_limiter.lua"));
        redisScript.setResultType(Long.class);
    }

    public boolean isAllowed(String apiKey, int capacity, int refillRate) {

        Long result = redisTemplate.execute(
                redisScript,
                Collections.singletonList("rate_limit:" + apiKey),
                String.valueOf(capacity),
                String.valueOf(refillRate),
                String.valueOf(System.currentTimeMillis())
        );

        return result != null && result == 1;
    }
}
