package com.example.olive_Cinema.repository;

import com.example.olive_Cinema.entity.Seat;
import com.example.olive_Cinema.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByIdAndReservedFalse(Long id);

    List<Seat> findByMovie(Movie movie); // 특정 영화의 모든 좌석 조회
}
