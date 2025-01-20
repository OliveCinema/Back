package com.example.olive_Cinema.service;

import com.example.olive_Cinema.entity.Seat;
import com.example.olive_Cinema.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final SeatRepository seatRepository;


    private static final String SEAT_LOCK_PREFIX = "seat:lock:";

    public boolean tryReserveSeat(Long seatId) {
        String lockKey = SEAT_LOCK_PREFIX + seatId;
        Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(lockKey, "LOCKED", Duration.ofSeconds(30));
        return Boolean.TRUE.equals(isLocked);
    }

    @Transactional
    public void confirmReservation(Long seatId) {
        String lockKey = "seat:lock:" + seatId;

        // Redis에서 "선택됨" 상태 확인
        String status = (String) redisTemplate.opsForValue().get(lockKey);
        if (!"SELECTED".equals(status)) {
            throw new IllegalStateException("Seat is not selected or selection has expired.");
        }

        // 좌석 예약 확정
        Seat seat = seatRepository.findByIdAndReservedFalse(seatId)
                .orElseThrow(() -> new IllegalArgumentException("Seat not available"));
        seat.setReserved(true);
        seatRepository.save(seat);

        // Redis에서 상태 제거
        redisTemplate.delete(lockKey);
    }


    public void cancelReservation(Long seatId) {
        String lockKey = SEAT_LOCK_PREFIX + seatId;
        redisTemplate.delete(lockKey);
    }

    public boolean selectSeat(Long seatId) {
        String lockKey = "seat:lock:" + seatId;

        // Redis에 "선택됨" 상태 저장 (TTL 2분)
        Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(lockKey, "SELECTED", Duration.ofMinutes(5));

        return Boolean.TRUE.equals(isLocked);
    }
}
