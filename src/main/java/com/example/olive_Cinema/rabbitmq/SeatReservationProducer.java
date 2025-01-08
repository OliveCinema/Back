package com.example.olive_Cinema.rabbitmq;

import com.example.olive_Cinema.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatReservationProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendReservationRequest(Long seatId) {
        System.out.println("Sending reservation request for Seat ID: " + seatId); // 디버그 로그
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                "seat.reserve.request",
                seatId
        );
        System.out.println("Message sent to RabbitMQ for Seat ID: " + seatId);
    }
}