package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.service.DateTimeService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DateTimeServiceImpl implements DateTimeService {


    @Override
    public Instant getCurrentDateTime() {
        return Instant.now();
    }
}
