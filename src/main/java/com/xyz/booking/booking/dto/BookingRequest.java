package com.xyz.booking.booking.dto;

import java.util.List;

public record BookingRequest(
        Long userId,
        Long showId,
        List<String> seatIds
) {}
