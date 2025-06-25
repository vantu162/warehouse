package com.example.redis.services;

import com.example.redis.repository.RedisKeyAndValueManager;
import com.example.redis.repository.RedisManager;
import com.example.redis.model.RedisResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServices implements RedisManager{

    private final RedisTemplate<String, String> redisTemplate;

    private final RedisKeyAndValueManager redisKeyAndValueManager;

    public RedisServices(RedisTemplate<String, String> redisTemplate, RedisKeyAndValueManager redisKeyAndValueManager) {
        this.redisTemplate = redisTemplate;
        this.redisKeyAndValueManager = redisKeyAndValueManager;
    }

    // lay username theo key
    @Override
    public RedisResponse<String> getUsername(String key) {
        Long ttl = redisTemplate.getExpire(key);
        System.out.println("Time to live còn lại: " + ttl + " giây");
//        if (ttl == -2) {
//            System.out.println("Key không tồn tại.");
//        } else if (ttl == -1) {
//            System.out.println("Key tồn tại nhưng không có TTL.");
//        } else {
//            System.out.println("TTL còn lại: " + ttl + " giây");
//        }

        String value = "Nguyen Van Tu";
        if(key.equals("a")){
            value = "Nguyen Xuan Tu";
        }

        if(ttl > 0){
            value = redisTemplate.opsForValue().get(key);
            System.out.println("redis cache: "+key+"/ value: " + value);
            return RedisResponse.<String>builder().code(200).data(value).build();
        }else{
            redisKeyAndValueManager.saveWithTTL(key, value, 36*100L);
        }
        return RedisResponse.<String>builder().code(200).data(value).build();

    }
}
