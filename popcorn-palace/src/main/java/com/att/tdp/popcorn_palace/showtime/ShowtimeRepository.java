package com.att.tdp.popcorn_palace.showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    @Query("SELECT COUNT(s) > 0 FROM Showtime s WHERE s.theater = :theater AND " +
            "(:startTime BETWEEN s.startTime AND s.endTime OR :endTime BETWEEN s.startTime AND s.endTime)")
    boolean existsOverlappingShowtime(@Param("theater") String theater,
                                      @Param("startTime") LocalDateTime startTime,
                                      @Param("endTime") LocalDateTime endTime);
}