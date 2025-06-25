package com.example.redis.controller;

import com.example.redis.repository.RedisManager;
import com.example.redis.model.RedisResponse;
import com.example.redis.model.RedisResquest;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    public final RedisManager redisManager;

    public RedisController(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    @PostMapping("/info")
    public RedisResponse<String> getUsername(@RequestBody RedisResquest redisResquest){
        return redisManager.getUsername(redisResquest.getKeyword());
    }
}
