package com.xyz.booking.booking.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long showId;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private BigDecimal totalAmount;
    private LocalDateTime createdAt;

    protected Booking() {}

    public Booking(Long userId, Long showId, BigDecimal totalAmount) {
        this.userId = userId;
        this.showId = showId;
        this.totalAmount = totalAmount;
        this.status = BookingStatus.INITIATED;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void confirm() {
        this.status = BookingStatus.CONFIRMED;
    }

    public void fail() {
        this.status = BookingStatus.FAILED;
    }


    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }
}
