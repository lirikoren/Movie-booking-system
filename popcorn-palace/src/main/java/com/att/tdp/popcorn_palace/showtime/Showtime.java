package com.att.tdp.popcorn_palace.showtime;

import com.att.tdp.popcorn_palace.movie.Movie;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "showtimes")
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @NotBlank(message = "Theater is required")
    private String theater;

    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @DecimalMin(value = "0.0", message = "Price must be positive")
    private double price;

    // Default constructor
    public Showtime() {}

    // Constructor with parameters
    public Showtime( Movie movie, String theater, LocalDateTime startTime, LocalDateTime endTime, double price) {
        this.movie = movie;
        this.theater = theater;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    // Getters
    public Long getId() {
        return id;
    }
    public Movie getMovie() { return movie; }
    public String getTheater() { return theater; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public double getPrice() { return price; }

    // Setters
    public void setMovie(Movie movie) { this.movie = movie; }
    public void setTheater(String theater) { this.theater = theater; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public void setPrice(double price) { this.price = price; }
    public void setId(long l) {this.id = id;}
}
