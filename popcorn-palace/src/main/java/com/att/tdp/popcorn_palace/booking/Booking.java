package com.att.tdp.popcorn_palace.booking;

import com.att.tdp.popcorn_palace.showtime.Showtime;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    @NotBlank(message = "Customer name is required")
    private String userId;

    @NotNull(message = "Seat number is required")
    private String seatNumber;

    public Booking() {}

    public Booking(Long id , Showtime showtime, String customerName, String seatNumber) {
        this.userId = customerName;
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public String getCustomerName() {
        return userId;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setSeatNumber(String seatNumber) {this.seatNumber = seatNumber;}
}