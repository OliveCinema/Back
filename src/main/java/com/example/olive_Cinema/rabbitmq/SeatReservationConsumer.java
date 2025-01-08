package com.example.olive_Cinema.rabbitmq;

import com.example.olive_Cinema.config.RabbitMQConfig;
import com.example.olive_Cinema.entity.Seat;
import com.example.olive_Cinema.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatReservationConsumer {

    private final SeatRepository seatRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME) // RabbitMQ의 큐에서 메시지 소비
    @Transactional //트랜잭션 추가
    public void handleReservationRequest(Long seatId) {
        System.out.println("Received reservation request for Seat ID: " + seatId); // 디버그 로그

        // DB에서 좌석 상태 업데이트
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("Seat not found for ID: " + seatId));
        seat.setReserved(true); // 좌석을 예약 상태로 변경
        seatRepository.save(seat); // DB에 업데이트

        System.out.println("Seat reservation updated in DB for Seat ID: " + seatId);
    }
}
