package com.ratelimiter.Project.service;

import com.ratelimiter.Project.entity.User;
import com.ratelimiter.Project.util.ApiKeyGenerator;
import com.ratelimiter.Project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User register(String email) {
        User user = new User();
        user.setEmail(email);
        user.setApiKey(ApiKeyGenerator.generateApiKey());
        return userRepository.save(user);
    }
}
