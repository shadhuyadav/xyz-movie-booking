package com.xyz.booking.common.util;

import java.util.UUID;

public final class IdGenerator {

    private IdGenerator() {}

    public static String generateIdempotencyKey() {
        return UUID.randomUUID().toString();
    }
}
