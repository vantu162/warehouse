package com.example.notify;

import com.google.gson.Gson;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerKafka {
    @KafkaListener(topics = "my-topic-02", groupId = "login")
    public void listen(Token message) {
        System.out.println("Nhận được từ service warehouse: " + new Gson().toJson(message));
    }
}
