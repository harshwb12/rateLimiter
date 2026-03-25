package com.ratelimiter.Project.interceptor;

import com.ratelimiter.Project.entity.User;
import com.ratelimiter.Project.repository.UserRepository;
import com.ratelimiter.Project.service.ApiKeyService;
import com.ratelimiter.Project.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@RequiredArgsConstructor
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiterService;
    private final ApiKeyService apiKeyService;
    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String apiKey = request.getHeader("x-api-key");

        if (apiKey == null) {
            response.setStatus(401);
            response.getWriter().write("Missing API Key");
            return false;
        }

        if (!apiKeyService.isValid(apiKey)) {
            response.setStatus(403);
            response.getWriter().write("Invalid API Key");
            return false;
        }
        User user = userRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!rateLimiterService.isAllowed(apiKey, user.getCapacity(),  user.getRefillRate())) {
            response.setStatus(429);
            response.getWriter().write("Too many requests");
            return false;
        }

        return true;
    }
}
