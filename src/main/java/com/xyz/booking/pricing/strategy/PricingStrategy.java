package com.xyz.booking.pricing.strategy;

import com.xyz.booking.pricing.PricingContext;

public interface PricingStrategy {

    void apply(PricingContext context);

    int order(); // lower runs first
}
