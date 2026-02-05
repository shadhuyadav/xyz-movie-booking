package com.xyz.booking.booking.dto;

import java.math.BigDecimal;

public record BookingResponse(
        Long bookingId,
        String status,
        BigDecimal totalAmount
) {}
