package com.xyz.booking.pricing.strategy;

import com.xyz.booking.pricing.PricingContext;

import java.math.BigDecimal;

public class ThirdTicketDiscountStrategy implements PricingStrategy {

    private final int seatCount;
    private final int pricePerSeat;

    public ThirdTicketDiscountStrategy(int seatCount, int pricePerSeat) {
        this.seatCount = seatCount;
        this.pricePerSeat = pricePerSeat;
    }

    @Override
    public void apply(PricingContext context) {
        if (seatCount >= 3) {
            BigDecimal discount =
                    BigDecimal.valueOf(pricePerSeat).multiply(BigDecimal.valueOf(0.5));
            context.addDiscount(discount, "50% off on 3rd Ticket");
        }
    }

    @Override
    public int order() {
        return 1;
    }
}
