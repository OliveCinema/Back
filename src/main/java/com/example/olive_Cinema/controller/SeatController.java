package com.example.olive_Cinema.controller;

import com.example.olive_Cinema.entity.Seat;
import com.example.olive_Cinema.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @PostMapping("/{seatId}/reserve")
    public ResponseEntity<Seat> reserveSeat(@PathVariable Long seatId) {
        Seat reservedSeat = seatService.reserveSeat(seatId);
        return ResponseEntity.ok(reservedSeat);
    }
}
