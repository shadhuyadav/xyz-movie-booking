package com.xyz.booking.seat.dto;

import java.util.List;

public record SeatLockRequest(
        Long showId,
        Long userId,
        List<String> seatIds
) {}
