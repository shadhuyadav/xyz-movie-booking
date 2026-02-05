package com.xyz.booking.pricing;

import java.time.LocalTime;

public record PricingRequest(
        int seatCount,
        int basePricePerSeat,
        LocalTime showTime
) {}
