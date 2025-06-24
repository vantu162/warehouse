package com.example.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

public class RedisServices implements RedisManager{

    private RedisTemplate<String, String> redisTemplate;

    // su dung redis de cahe du lieu theo key va value
    @Override
    public void saveUsername(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // lay username theo key
    public String getUsername(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void saveWithTTL(String key, Object value, long seconds) {
        redisTemplate.opsForValue().set(key, (String) value, Duration.ofSeconds(seconds));

    }


    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    public void deleteAll() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }
}
