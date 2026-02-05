package com.xyz.booking.browse.dto;

import java.time.LocalTime;

public record BrowseShowResponse(
        String theatreName,
        String screenName,
        LocalTime showTime,
        long availableSeats
) {}
