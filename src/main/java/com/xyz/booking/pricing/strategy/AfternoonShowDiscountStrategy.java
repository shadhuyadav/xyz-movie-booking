package com.xyz.booking.pricing.strategy;

import com.xyz.booking.pricing.PricingContext;

import java.math.BigDecimal;
import java.time.LocalTime;

public class AfternoonShowDiscountStrategy implements PricingStrategy {

    private final LocalTime showTime;

    public AfternoonShowDiscountStrategy(LocalTime showTime) {
        this.showTime = showTime;
    }

    @Override
    public void apply(PricingContext context) {
        if (!showTime.isBefore(LocalTime.NOON)
                && showTime.isBefore(LocalTime.of(16, 0))) {

            BigDecimal discount =
                    context.baseAmount().multiply(BigDecimal.valueOf(0.20));

            context.addDiscount(discount, "20% Afternoon Show Discount");
        }
    }

    @Override
    public int order() {
        return 2;
    }
}
