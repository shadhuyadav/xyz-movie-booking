package com.xyz.booking.booking.dto;

import java.util.List;

public record BulkBookingRequest(
        List<BookingRequest> bookings
) {}
