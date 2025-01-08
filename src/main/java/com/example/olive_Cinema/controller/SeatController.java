package com.example.olive_Cinema.controller;

import com.example.olive_Cinema.entity.Movie;
import com.example.olive_Cinema.entity.Seat;
import com.example.olive_Cinema.rabbitmq.SeatReservationProducer;
import com.example.olive_Cinema.repository.MovieRepository;
import com.example.olive_Cinema.repository.SeatRepository;
import com.example.olive_Cinema.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;
    private final SeatReservationProducer seatReservationProducer;
    private final SeatRepository seatRepository;
    private final MovieRepository movieRepository;

    @PostMapping("/{seatId}/reserve")
    public ResponseEntity<String> reserveSeat(@PathVariable Long seatId) {
        seatReservationProducer.sendReservationRequest(seatId);
        return ResponseEntity.ok("Reservation request sent.");
    }

    @PostMapping("/{seatId}/confirm")
    public ResponseEntity<String> confirmReservation(@PathVariable Long seatId) {
        try {
            seatReservationProducer.sendReservationRequest(seatId); // RabbitMQ로 메시지 전송
            return ResponseEntity.ok("Reservation request sent.");
        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to confirm reservation: " + e.getMessage());
        }
    }




    @PostMapping("/{seatId}/cancel")
    public ResponseEntity<String> cancelReservation(@PathVariable Long seatId) {
        seatService.cancelReservation(seatId);
        return ResponseEntity.ok("Reservation cancelled.");
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Seat>> getSeatsByMovie(@PathVariable Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        List<Seat> seats = seatRepository.findByMovie(movie);
        return ResponseEntity.ok(seats);
    }

    @PostMapping("/{seatId}/select")
    public ResponseEntity<String> selectSeat(@PathVariable Long seatId) {
        boolean isSelected = seatService.selectSeat(seatId);
        if (isSelected) {
            return ResponseEntity.ok("Seat selected successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Seat is already selected by another user.");
        }
    }

}
