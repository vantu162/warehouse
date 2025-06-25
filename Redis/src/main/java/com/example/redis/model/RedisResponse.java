package com.example.redis.model;

import lombok.Builder;

@Builder
public class RedisResponse<T> {
    public int code;
    public T data;
}
