package com.xyz.booking.pricing;

import java.math.BigDecimal;
import java.util.List;

public record PricingResponse(
        BigDecimal baseAmount,
        BigDecimal discount,
        BigDecimal finalAmount,
        List<String> appliedOffers
) {}
