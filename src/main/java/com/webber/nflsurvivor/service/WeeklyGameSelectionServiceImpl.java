package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.domain.User;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import com.webber.nflsurvivor.repository.WeeklyGameSelectionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class WeeklyGameSelectionServiceImpl implements WeeklyGameSelectionService {

    private final WeeklyGameSelectionRepository weeklyGameSelectionRepository;

    @Autowired
    public WeeklyGameSelectionServiceImpl(WeeklyGameSelectionRepository weeklyGameSelectionRepository) {
        this.weeklyGameSelectionRepository = weeklyGameSelectionRepository;
    }

    @Override
    public WeeklyGameSelection create(WeeklyGameSelection weeklyGameSelection) {
        if (weeklyGameSelection.getId() != null) {
            throw new IllegalArgumentException("WeeklyGameSelection " + weeklyGameSelection + " already exists");
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
