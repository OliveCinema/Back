package com.example.olive_Cinema.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    private boolean reserved;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
