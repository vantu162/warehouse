package com.example.redis.services;

import com.example.redis.repository.RedisKeyAndValueManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class RedisKeyAndValueServices implements RedisKeyAndValueManager {

    private final RedisTemplate<String, String> redisTemplate;
    public RedisKeyAndValueServices(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveUsername(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void saveWithTTL(String key, String value, long seconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(seconds));
    }

//    public void deleteKey(String key) {
//        redisTemplate.delete(key);
//    }
//
//    public void deleteAll() {
//        redisTemplate.getConnectionFactory().getConnection().flushAll();
//    }
}
