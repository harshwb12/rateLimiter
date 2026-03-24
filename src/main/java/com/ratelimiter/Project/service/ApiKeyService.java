package com.ratelimiter.Project.service;

import com.ratelimiter.Project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiKeyService {

    private final UserRepository userRepository;

    public boolean isValid(String apiKey) {
        return userRepository.findByApiKey(apiKey).isPresent();
    }
}
