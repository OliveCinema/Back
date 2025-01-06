package com.example.olive_Cinema.repository;

import com.example.olive_Cinema.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByIdAndReservedFalse(Long id);
}
