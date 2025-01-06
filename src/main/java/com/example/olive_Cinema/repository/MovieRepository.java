package com.example.olive_Cinema.repository;

import com.example.olive_Cinema.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
