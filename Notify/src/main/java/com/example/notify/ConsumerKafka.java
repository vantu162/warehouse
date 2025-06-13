package com.example.notify;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerKafka {
    @KafkaListener(topics = "my-topic", groupId = "login")
    public void listen(String message) {
        System.out.println("Nhận được từ service warehouse: " + message);
    }
}
