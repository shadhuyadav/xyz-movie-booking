package com.xyz.booking.booking.dto;

import java.util.List;

public record BulkBookingResponse(
        List<BookingResult> results
) {
    public record BookingResult(
            Long bookingId,
            String status,
            String message
    ) {}
}
