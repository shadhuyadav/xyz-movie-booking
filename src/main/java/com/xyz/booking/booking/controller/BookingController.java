package com.xyz.booking.booking.controller;

import com.xyz.booking.booking.dto.*;
import com.xyz.booking.booking.service.BookingService;
import com.xyz.booking.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping
    public ResponseEntity<BookingResponse> bookTickets(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody BookingRequest request
    ) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new BusinessException("Idempotency-Key header is required");
        }

        BookingResponse response =
                bookingService.bookTickets(idempotencyKey, request);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/bulk")
    public ResponseEntity<BulkBookingResponse> bulkBook(
            @RequestBody BulkBookingRequest request
    ) {
        BulkBookingResponse response =
                bookingService.bulkBook(request);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/bulk/cancel")
    public ResponseEntity<String> bulkCancel(
            @RequestBody BulkCancellationRequest request
    ) {
        if (request.bookingIds() == null || request.bookingIds().isEmpty()) {
            throw new BusinessException("bookingIds are required");
        }

        request.bookingIds()
                .forEach(bookingService::cancelBooking);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Bulk cancellation completed");
    }


    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<String> cancelBooking(
            @PathVariable Long bookingId
    ) {
        bookingService.cancelBooking(bookingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Booking cancelled successfully");
    }
}
