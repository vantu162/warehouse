package com.example.redis.repository;

public interface RedisKeyAndValueManager {
    void saveUsername(String key, String value);
    void saveWithTTL(String key, String value, long seconds);
}
