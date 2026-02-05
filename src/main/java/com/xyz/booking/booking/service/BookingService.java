package com.xyz.booking.booking.service;

import com.xyz.booking.booking.dto.*;

public interface BookingService {



    BulkBookingResponse bulkBook(BulkBookingRequest request);

    void cancelBooking(Long bookingId);

    BookingResponse bookTickets(
            String idempotencyKey,
            BookingRequest request
    );

}
