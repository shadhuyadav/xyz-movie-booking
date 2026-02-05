package com.xyz.booking.show.repository;

import com.xyz.booking.show.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Long> {

    Optional<Show> findByScreenIdAndShowDateAndStartTime(
            Long screenId,
            LocalDate showDate,
            LocalTime startTime
    );
}
