package com.xyz.booking.seat.service;

import com.xyz.booking.seat.dto.SeatLockRequest;

public interface SeatService {

    void lockSeats(SeatLockRequest request);

    void confirmSeats(Long showId, Iterable<String> seatIds);

    void releaseSeats(Long showId, Iterable<String> seatIds);
}
