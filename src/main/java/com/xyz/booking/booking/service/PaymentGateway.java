package com.xyz.booking.booking.service;

import java.math.BigDecimal;
import java.util.Random;

public class PaymentGateway {

    private static final Random RANDOM = new Random();

    public static boolean charge(BigDecimal amount) {

        return RANDOM.nextInt(10) < 8;
    }
}
