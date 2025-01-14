package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.*;
import com.webber.nflsurvivor.game.Game;
import com.webber.nflsurvivor.game.GameResult;
import com.webber.nflsurvivor.repository.WeeklyGameSelectionRepository;
import com.webber.nflsurvivor.util.DateTimeService;
import com.webber.nflsurvivor.service.TeamService;
import com.webber.nflsurvivor.service.WeeklyGameSelectionService;
import com.webber.nflsurvivor.user.User;
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
            wgs.setGameResult(getGameResultForSelection(wgs));
        });
        return weeklyGameSelections;
    }

    private GameResult getGameResultForSelection(WeeklyGameSelection selection) {
        Game game = selection.getSelectedGame();
        Team selectedTeam = selection.getWinningTeamSelection();
        if (!game.isFinished()) {
            return null;
        }
        if (game.getWinningTeam() == null) {
            return GameResult.TIE;
        } else if (game.getWinningTeam().equals(selectedTeam)) {
            return GameResult.WIN;
        } else {
            return GameResult.LOSS;
        }
    }

    @Override
    public List<WeeklyGameSelection> findAllForWeek(int week) {
        return weeklyGameSelectionRepository.findAllByWeek(week);
    }
}
