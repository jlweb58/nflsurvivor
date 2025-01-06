package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.GameWillStartSoonException;
import com.webber.nflsurvivor.domain.TeamAlreadySelectedException;
import com.webber.nflsurvivor.domain.User;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import com.webber.nflsurvivor.domain.WeeklyTeamScore;
import com.webber.nflsurvivor.repository.WeeklyGameSelectionRepository;
import com.webber.nflsurvivor.service.DateTimeService;
import com.webber.nflsurvivor.service.TeamService;
import com.webber.nflsurvivor.service.WeeklyGameSelectionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class WeeklyGameSelectionServiceImpl implements WeeklyGameSelectionService {

    private final WeeklyGameSelectionRepository weeklyGameSelectionRepository;

    private final DateTimeService dateTimeService;
    private final TeamService teamService;

    @Autowired
    public WeeklyGameSelectionServiceImpl(WeeklyGameSelectionRepository weeklyGameSelectionRepository, DateTimeService dateTimeService, TeamService teamService) {
        this.weeklyGameSelectionRepository = weeklyGameSelectionRepository;
        this.dateTimeService = dateTimeService;
        this.teamService = teamService;
    }

    @Override
    public WeeklyGameSelection create(WeeklyGameSelection weeklyGameSelection) throws GameWillStartSoonException, TeamAlreadySelectedException {
        if (weeklyGameSelection.getId() != null) {
            throw new IllegalArgumentException("WeeklyGameSelection " + weeklyGameSelection + " already exists");
        }
        Instant now = dateTimeService.getCurrentDateTime();
        now = now.plus(15, ChronoUnit.MINUTES);
        Instant gameTime = weeklyGameSelection.getSelectedGame().getStartTime();
        if (now.isAfter(gameTime)) {
            throw new GameWillStartSoonException("Game start time " + gameTime + " is too close to current time: " + now.plus(15, ChronoUnit.MINUTES));
        }
        List<WeeklyGameSelection> allUserGameSelections = weeklyGameSelectionRepository.findAllByUser(weeklyGameSelection.getUser());
        boolean isTeamAlreadyPicked = allUserGameSelections.stream().anyMatch(s -> s.getWinningTeamSelection().equals(weeklyGameSelection.getWinningTeamSelection()));
        if (isTeamAlreadyPicked) {
            throw new TeamAlreadySelectedException("Team " + weeklyGameSelection.getWinningTeamSelection() + " was already selected by this user");
        }
        return weeklyGameSelectionRepository.save(weeklyGameSelection);
    }

    @Override
    public List<WeeklyGameSelection> findForUser(User user) {
        List<WeeklyGameSelection> weeklyGameSelections = weeklyGameSelectionRepository.findAllByUser(user);
        weeklyGameSelections.forEach(wgs -> {
            WeeklyTeamScore selectedTeamScore = teamService.getWeeklyTeamScoreByTeamId(wgs.getWinningTeamSelection().getId(), wgs.getWeek());
            wgs.getWinningTeamSelection().setWeeklyTeamScore(selectedTeamScore);
        });
        return weeklyGameSelections;
    }

    @Override
    public List<WeeklyGameSelection> findAllForWeek(int week) {
        return weeklyGameSelectionRepository.findAllByWeek(week);
    }
}
