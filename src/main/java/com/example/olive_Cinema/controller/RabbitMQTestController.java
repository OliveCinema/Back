package com.example.olive_Cinema.controller;

import com.example.olive_Cinema.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQTestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/test-rabbitmq")
    public String testRabbitMQ() {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    "seat.reserve.request",  // 바인딩된 키와 일치하는 키 사용
                    "Test Message"
            );

            return "Message sent to RabbitMQ!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send message to RabbitMQ: " + e.getMessage();
        }
    }
}
