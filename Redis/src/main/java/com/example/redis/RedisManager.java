package com.example.redis;

public interface RedisManager {
    void saveUsername(String key, String value);
    public void saveWithTTL(String key, Object value, long seconds);
}
