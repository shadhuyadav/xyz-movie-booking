package com.xyz.booking.pricing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PricingContext {

    private BigDecimal baseAmount;
    private BigDecimal discount = BigDecimal.ZERO;
    private final List<String> appliedOffers = new ArrayList<>();

    public PricingContext(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public BigDecimal baseAmount() {
        return baseAmount;
    }

    public BigDecimal discount() {
        return discount;
    }

    public void addDiscount(BigDecimal value, String offerName) {
        discount = discount.add(value);
        appliedOffers.add(offerName);
    }

    public BigDecimal finalAmount() {
        return baseAmount.subtract(discount);
    }

    public List<String> appliedOffers() {
        return appliedOffers;
    }
}
