package com.att.tdp.popcorn_palace;

import com.att.tdp.popcorn_palace.booking.Booking;
import com.att.tdp.popcorn_palace.booking.BookingRepository;
import com.att.tdp.popcorn_palace.booking.BookingService;
import com.att.tdp.popcorn_palace.showtime.Showtime;
import com.att.tdp.popcorn_palace.showtime.ShowtimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ShowtimeRepository showtimeRepository;

    @InjectMocks
    private BookingService bookingService;

    private Showtime showtime;
    private Booking booking;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock Showtime and Booking objects
        showtime = new Showtime();
        showtime.setId(1L);
        showtime.setTheater("Theater 1");
        showtime.setStartTime(LocalDateTime.now().plusHours(1));
        showtime.setEndTime(LocalDateTime.now().plusHours(3));

        booking = new Booking();
        booking.setSeatNumber("A1");
    }

    @Test
    void testBookTicket_Success() {
        // Mock behavior of the repositories
        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));
        when(bookingRepository.existsByShowtimeIdAndSeatNumber(1L, "A1")).thenReturn(false);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Call the bookTicket method
        Booking bookedTicket = bookingService.bookTicket(1L, booking);

        // Assert that the booking was successful
        assertNotNull(bookedTicket);
        assertEquals(showtime, bookedTicket.getShowtime());
        assertEquals("A1", bookedTicket.getSeatNumber());
    }

    @Test
    void testBookTicket_SeatAlreadyTaken() {
        // Mock behavior for when the seat is already taken
        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));
        when(bookingRepository.existsByShowtimeIdAndSeatNumber(1L, "A1")).thenReturn(true);

        // Call the bookTicket method and assert that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.bookTicket(1L, booking);
        });

        // Verify the exception message
        assertEquals("Seat is already booked for this showtime.", exception.getMessage());
    }

    @Test
    void testBookTicket_ShowtimeNotFound() {
        // Mock behavior for when the showtime is not found
        when(showtimeRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the bookTicket method and assert that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.bookTicket(1L, booking);
        });

        // Verify the exception message
        assertEquals("Showtime not found", exception.getMessage());
    }
}
