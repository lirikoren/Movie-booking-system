package com.att.tdp.popcorn_palace.booking;

import com.att.tdp.popcorn_palace.showtime.Showtime;
import com.att.tdp.popcorn_palace.showtime.ShowtimeRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private ShowtimeRepository showtimeRepository;

    public BookingService(BookingRepository bookingRepository, ShowtimeRepository showtimeRepository){
        this.bookingRepository = bookingRepository;
        this.showtimeRepository = showtimeRepository;
    }

    public Booking bookTicket(Long showtimeId, Booking booking) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        // Ensure no seat is booked twice for the same showtime
        boolean seatTaken = bookingRepository.existsByShowtimeIdAndSeatNumber(showtimeId, booking.getSeatNumber());
        if (seatTaken) {
            throw new RuntimeException("Seat is already booked for this showtime.");
        }

        booking.setShowtime(showtime);
        return bookingRepository.save(booking);
    }
}
