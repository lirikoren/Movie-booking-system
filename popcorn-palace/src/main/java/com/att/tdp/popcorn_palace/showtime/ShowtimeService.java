package com.att.tdp.popcorn_palace.showtime;

import com.att.tdp.popcorn_palace.movie.Movie;
import com.att.tdp.popcorn_palace.movie.MovieRepository;
import org.springframework.stereotype.Service;


@Service
public class ShowtimeService {

    private ShowtimeRepository showtimeRepository;
    private MovieRepository movieRepository;

    public ShowtimeService(ShowtimeRepository showtimeRepository , MovieRepository movieRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }

    public Showtime addShowtime(Long movieId , Showtime showtime) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        showtime.setMovie(movie);

        // Check for overlapping showtime in the same theater
        if (showtimeRepository.existsOverlappingShowtime(
                showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime())) {
            throw new RuntimeException("Overlapping showtime exists in the same theater.");
        }

        return showtimeRepository.save(showtime);
    }

    public Showtime getShowtimeById(Long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
    }

    public Showtime updateShowtime(Long id, Showtime updatedShowtime) {
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        // Update showtime details
        showtime.setTheater(updatedShowtime.getTheater());
        showtime.setStartTime(updatedShowtime.getStartTime());
        showtime.setEndTime(updatedShowtime.getEndTime());
        showtime.setPrice(updatedShowtime.getPrice());

        // Check for overlapping showtimes in the same theater
        if (showtimeRepository.existsOverlappingShowtime(
                showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime())) {
            throw new RuntimeException("Overlapping showtime exists in the same theater.");
        }

        return showtimeRepository.save(showtime);
    }

    public void deleteShowtime(Long id) {
        if (!showtimeRepository.existsById(id)) {
            throw new RuntimeException("Showtime not found");
        }
        showtimeRepository.deleteById(id);
    }
}
