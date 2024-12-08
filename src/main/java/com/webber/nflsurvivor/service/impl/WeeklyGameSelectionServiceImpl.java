package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.GameWillStartSoonException;
import com.webber.nflsurvivor.domain.User;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import com.webber.nflsurvivor.repository.WeeklyGameSelectionRepository;
import com.webber.nflsurvivor.service.DateTimeService;
import com.webber.nflsurvivor.service.WeeklyGameSelectionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class WeeklyGameSelectionServiceImpl implements WeeklyGameSelectionService {

    private final WeeklyGameSelectionRepository weeklyGameSelectionRepository;

    private final DateTimeService dateTimeService;

    @Autowired
    public WeeklyGameSelectionServiceImpl(WeeklyGameSelectionRepository weeklyGameSelectionRepository, DateTimeService dateTimeService) {
        this.weeklyGameSelectionRepository = weeklyGameSelectionRepository;
        this.dateTimeService = dateTimeService;
    }

    @Override
    public WeeklyGameSelection create(WeeklyGameSelection weeklyGameSelection) throws GameWillStartSoonException {
        if (weeklyGameSelection.getId() != null) {
            throw new IllegalArgumentException("WeeklyGameSelection " + weeklyGameSelection + " already exists");
        }
        ZonedDateTime now = dateTimeService.getCurrentDateTime();
        now = now.plusMinutes(15);
        ZonedDateTime gameTime = weeklyGameSelection.getSelectedGame().getStartTime();
        if (!now.isAfter(gameTime)) {
            throw new GameWillStartSoonException("Game start time " + gameTime + " is too close to current time: " + now.plusMinutes(15));
        }

        return weeklyGameSelectionRepository.save(weeklyGameSelection);
    }

    @Override
    public List<WeeklyGameSelection> findForUser(User user) {
        return weeklyGameSelectionRepository.findAllByUser(user);
    }

    @Override
    public List<WeeklyGameSelection> findAllForWeek(int week) {
        return weeklyGameSelectionRepository.findAllByWeek(week);
    }
}
