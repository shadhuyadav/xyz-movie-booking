package com.xyz.booking.booking.dto;

import java.util.List;

public record BulkCancellationRequest(
        List<Long> bookingIds
) {}
