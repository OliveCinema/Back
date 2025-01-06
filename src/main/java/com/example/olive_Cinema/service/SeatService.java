package com.example.olive_Cinema.service;

import com.example.olive_Cinema.entity.Seat;
import com.example.olive_Cinema.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    public Seat reserveSeat(Long seatId) {
        Seat seat = seatRepository.findByIdAndReservedFalse(seatId)
                .orElseThrow(() -> new IllegalArgumentException("Seat not available"));
        seat.setReserved(true);
        return seatRepository.save(seat);
    }
}
