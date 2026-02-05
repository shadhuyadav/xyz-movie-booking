package com.xyz.booking.pricing.strategy;

import com.xyz.booking.pricing.PricingContext;

public class BasePricingStrategy implements PricingStrategy {

    @Override
    public void apply(PricingContext context) {
        // Base price already initialized
    }

    @Override
    public int order() {
        return 0;
    }
}
