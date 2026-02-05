package com.xyz.booking.pricing;

import com.xyz.booking.pricing.strategy.*;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class PricingService {

    public PricingResponse calculate(PricingRequest request) {

        BigDecimal baseAmount =
                BigDecimal.valueOf(request.seatCount())
                        .multiply(BigDecimal.valueOf(request.basePricePerSeat()));

        PricingContext context = new PricingContext(baseAmount);

        List<PricingStrategy> strategies = List.of(
                new BasePricingStrategy(),
                new ThirdTicketDiscountStrategy(
                        request.seatCount(),
                        request.basePricePerSeat()
                ),
                new AfternoonShowDiscountStrategy(request.showTime())
        );

        strategies.stream()
                .sorted(Comparator.comparingInt(PricingStrategy::order))
                .forEach(strategy -> strategy.apply(context));

        return new PricingResponse(
                context.baseAmount(),
                context.discount(),
                context.finalAmount(),
                context.appliedOffers()
        );
    }
}
