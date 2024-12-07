package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.service.DateTimeService;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class DateTimeServiceImpl implements DateTimeService {

    private final static String DEFAULT_ZONE_ID = "Europe/Berlin";

    @Override
    public ZonedDateTime getCurrentDateTime() {
        return ZonedDateTime.now(ZoneId.of(DEFAULT_ZONE_ID));
    }
}
