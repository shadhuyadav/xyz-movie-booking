package com.xyz.booking.seat.controller;

import com.xyz.booking.seat.dto.SeatLockRequest;
import com.xyz.booking.seat.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/lock")
    public ResponseEntity<String> lockSeats(@RequestBody SeatLockRequest request) {
        seatService.lockSeats(request);
        return ResponseEntity.ok("Seats locked successfully");
    }
}
