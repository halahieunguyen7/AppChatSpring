package com.example.ChatApp.Domain.GeneralService;

import java.security.SecureRandom;
import java.util.UUID;

public class UUIDv7 {
    public static UUID generateUuidV7() {
        long timestamp = System.currentTimeMillis();

        long msb = timestamp << 16;
        msb |= 0x7000; // version 7

        long lsb = new SecureRandom().nextLong();
        lsb &= 0x3fffffffffffffffL;
        lsb |= 0x8000000000000000L;

        return new UUID(msb, lsb);
    }
}
