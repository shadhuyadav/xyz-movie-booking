package com.xyz.booking.booking.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private int retryCount;
    private LocalDateTime createdAt;

    protected Payment() {}

    public Payment(Long bookingId, BigDecimal amount) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.status = PaymentStatus.INITIATED;
        this.retryCount = 0;
        this.createdAt = LocalDateTime.now();
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void markSuccess() {
        this.status = PaymentStatus.SUCCESS;
    }

    public void markFailed() {
        this.status = PaymentStatus.FAILED;
        this.retryCount++;
    }
}
