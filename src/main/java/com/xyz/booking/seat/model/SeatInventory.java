package com.xyz.booking.seat.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "seat_inventory",
        uniqueConstraints = @UniqueConstraint(columnNames = {"show_id", "seat_id"})
)
public class SeatInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "show_id", nullable = false)
    private Long showId;

    @Column(name = "seat_id", nullable = false)
    private String seatId;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @Version
    private Integer version;

    protected SeatInventory() {}

    public SeatInventory(Long showId, String seatId) {
        this.showId = showId;
        this.seatId = seatId;
        this.status = SeatStatus.AVAILABLE;
    }

    public void markBooked() {
        this.status = SeatStatus.BOOKED;
    }

    public SeatStatus getStatus() {
        return status;
    }
}
