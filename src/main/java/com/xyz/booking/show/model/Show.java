package com.xyz.booking.show.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(
        name = "shows",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"screen_id", "show_date", "start_time"}
        )
)
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movieId;

    private Long screenId;

    private LocalDate showDate;

    private LocalTime startTime;

    private int basePrice;

    protected Show() {}

    public Show(Long movieId,
                Long screenId,
                LocalDate showDate,
                LocalTime startTime,
                int basePrice) {

        this.movieId = movieId;
        this.screenId = screenId;
        this.showDate = showDate;
        this.startTime = startTime;
        this.basePrice = basePrice;
    }

    public Long getId() {
        return id;
    }

    public int getBasePrice() {
        return basePrice;
    }
}
