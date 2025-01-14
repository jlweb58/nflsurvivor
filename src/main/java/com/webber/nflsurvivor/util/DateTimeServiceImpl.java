package com.webber.nflsurvivor.util;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.function.Supplier;

@Service
public class DateTimeServiceImpl implements DateTimeService {

    private final Supplier<Instant> timeSupplier;

    public DateTimeServiceImpl(Supplier<Instant> timeSupplier) {
        this.timeSupplier = timeSupplier;
    }

    public Instant getCurrentDateTime() {
        return timeSupplier.get();
    }
}
