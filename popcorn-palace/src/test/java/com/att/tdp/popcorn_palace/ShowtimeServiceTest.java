package com.att.tdp.popcorn_palace;

import com.att.tdp.popcorn_palace.movie.Movie;
import com.att.tdp.popcorn_palace.movie.MovieService;
import com.att.tdp.popcorn_palace.movie.MovieRepository;
import com.att.tdp.popcorn_palace.showtime.Showtime;
import com.att.tdp.popcorn_palace.showtime.ShowtimeRepository;
import com.att.tdp.popcorn_palace.showtime.ShowtimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowtimeServiceTest {

    @Mock
    private ShowtimeRepository showtimeRepository;

    @Mock
    private MovieRepository movieRepository; // Mock the MovieRepository
    @Mock
    private MovieService movieService;

    @InjectMocks
    private ShowtimeService showtimeService;

    private Movie movie;
    private Showtime showtime;
    private Showtime updatedShowtime;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create the movie (without an ID at first)
        movie = new Movie("Inception", "Sci-Fi", 148, 7.5, 2010);
        movie.setId(1L);
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie)); // Mock the movieRepository's findById
        when(movieService.addMovie(any(Movie.class))).thenReturn(movie); // Mock the movieService

        // Create a showtime to add, update, and delete
        showtime = new Showtime(movie, "Theater 1",
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2), 12.99);
        showtime.setId(1L);

        // Create the updated showtime with new details
        updatedShowtime = new Showtime(movie, "Theater 1",
                LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(2), 14.99);
    }

    @Test
    void testUpdateShowtime() {
        // Mock showtimeRepository to return the existing showtime
        when(showtimeRepository.findById(showtime.getId())).thenReturn(Optional.of(showtime));

        // Mock saving the updated showtime
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(updatedShowtime);

        // Call the method to update the showtime
        Showtime updated = showtimeService.updateShowtime(showtime.getId(), updatedShowtime);

        // Assertions to check the update logic
        assertNotNull(updated);
        assertEquals(updatedShowtime.getStartTime(), updated.getStartTime());
        assertEquals(updatedShowtime.getEndTime(), updated.getEndTime());
        assertEquals(updatedShowtime.getPrice(), updated.getPrice());
    }

    @Test
    void testUpdateShowtime_ShowtimeNotFound() {
        // Simulate the showtime being not found
        when(showtimeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Expecting an exception when trying to update a non-existent showtime
        assertThrows(RuntimeException.class, () -> showtimeService.updateShowtime(999L, updatedShowtime));
    }

    @Test
    void testDeleteShowtime() {
        // Mock showtimeRepository to confirm the showtime exists
        when(showtimeRepository.existsById(showtime.getId())).thenReturn(true);

        // Call the method to delete the showtime
        showtimeService.deleteShowtime(showtime.getId());

        // Verify that deleteById was called once
        verify(showtimeRepository, times(1)).deleteById(showtime.getId());
    }

    @Test
    void testDeleteShowtime_ShowtimeNotFound() {
        // Simulate the showtime being not found for deletion
        when(showtimeRepository.existsById(anyLong())).thenReturn(false);

        // Expecting an exception when trying to delete a non-existent showtime
        assertThrows(RuntimeException.class, () -> showtimeService.deleteShowtime(999L));
    }
}
