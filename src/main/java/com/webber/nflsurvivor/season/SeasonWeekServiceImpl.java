package com.webber.nflsurvivor.season;

import com.webber.nflsurvivor.util.DateTimeService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SeasonWeekServiceImpl implements SeasonWeekService {

    private final SeasonWeekRepository seasonWeekRepository;

    private final DateTimeService dateTimeService;

    public SeasonWeekServiceImpl(SeasonWeekRepository seasonWeekRepository, DateTimeService dateTimeService) {
        this.seasonWeekRepository = seasonWeekRepository;
        this.dateTimeService = dateTimeService;
    }

    @Override
    public Integer getActiveGameWeek() {
        Instant now = dateTimeService.getCurrentDateTime();
        SeasonWeek seasonWeek = seasonWeekRepository.findByInstantBetweenStartAndEnd(now);
        if (seasonWeek == null) {
            return 0;
        }
        return seasonWeek.getWeekNumber();
    }
}
