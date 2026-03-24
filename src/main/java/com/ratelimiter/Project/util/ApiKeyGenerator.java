package com.ratelimiter.Project.util;

import java.util.UUID;

public class ApiKeyGenerator {

    public static String generateApiKey() {
        return UUID.randomUUID().toString();
    }
}
