package com.ratelimiter.Project.controller;

import com.ratelimiter.Project.entity.User;
import com.ratelimiter.Project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestParam String email) {
        return userService.register(email);
    }
}
