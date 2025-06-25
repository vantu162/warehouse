package com.example.redis.repository;

import com.example.redis.model.RedisResponse;

public interface RedisManager {
    RedisResponse<String> getUsername(String key);
}
