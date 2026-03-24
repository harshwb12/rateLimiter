package com.ratelimiter.Project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsageController {

    private final StringRedisTemplate redisTemplate;

    @GetMapping("/usage")
    public String usage(@RequestHeader("x-api-key") String apiKey) {
        String data = redisTemplate.opsForValue().get("rate_limit:" + apiKey);
        return data == null ? "No usage yet" : data;
    }
}