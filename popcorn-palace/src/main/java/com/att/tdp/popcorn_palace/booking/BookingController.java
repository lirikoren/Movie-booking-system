package com.att.tdp.popcorn_palace.booking;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/{showtimeId}")
    public ResponseEntity<Booking> bookTicket(@PathVariable Long showtimeId, @Valid @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.bookTicket(showtimeId, booking));
    }
}
