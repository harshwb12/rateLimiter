package com.ratelimiter.Project.controller;

import com.ratelimiter.Project.entity.User;
import com.ratelimiter.Project.repository.UserRepository;
import com.ratelimiter.Project.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

@RestController
@RequestMapping("/rate-limit")
@RequiredArgsConstructor
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    @GetMapping("/check")
    public Map<String, Object> check(@RequestHeader("x-api-key") String apiKey) {

        User user = userRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Invalid API key"));

        boolean allowed = rateLimiterService.isAllowed(
                apiKey,
                user.getCapacity(),
                user.getRefillRate()
        );

        String data = redisTemplate.opsForValue().get("rate_limit:" + apiKey);

        int remainingTokens = -1;
        if (data != null) {
            String[] parts = data.split(":");
            remainingTokens = Integer.parseInt(parts[0]);
        }

        return Map.of(
                "allowed", allowed,
                "remainingTokens", remainingTokens,
                "timestamp", System.currentTimeMillis()
        );
    }
}