package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.*;
import com.webber.nflsurvivor.game.Game;
import com.webber.nflsurvivor.game.GameResult;
import com.webber.nflsurvivor.repository.TeamRepository;
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
import java.util.Optional;

@Service
@Transactional
public class WeeklyGameSelectionServiceImpl implements WeeklyGameSelectionService {

    private final WeeklyGameSelectionRepository weeklyGameSelectionRepository;

    private final DateTimeService dateTimeService;
    private final TeamService teamService;
    private final TeamRepository teamRepository;

    @Autowired
    public WeeklyGameSelectionServiceImpl(WeeklyGameSelectionRepository weeklyGameSelectionRepository, DateTimeService dateTimeService, TeamService teamService, TeamRepository teamRepository) {
        this.weeklyGameSelectionRepository = weeklyGameSelectionRepository;
        this.dateTimeService = dateTimeService;
        this.teamService = teamService;
        this.teamRepository = teamRepository;
    }

    @Override
    public WeeklyGameSelection create(WeeklyGameSelection weeklyGameSelection) throws GameWillStartSoonException, TeamAlreadySelectedException {
        if (weeklyGameSelection.getId() != null) {
            throw new IllegalArgumentException("WeeklyGameSelection " + weeklyGameSelection + " already exists");
        }
        boolean isSelectionTooLate = isSelectionTooLate(weeklyGameSelection);
        if (isSelectionTooLate) {
            throw new GameWillStartSoonException("Game start time is too close to current time");
        }
        if (isTeamAlreadyPicked(weeklyGameSelection.getWinningTeamSelection(), weeklyGameSelection.getUser())) {
            throw new TeamAlreadySelectedException("Team " + weeklyGameSelection.getWinningTeamSelection() + " was already selected by this user");
        }
        return weeklyGameSelectionRepository.save(weeklyGameSelection);
    }

    private boolean isTeamAlreadyPicked(Team team, User user) {
        List<WeeklyGameSelection> allUserGameSelections = weeklyGameSelectionRepository.findAllByUser(user);
        return allUserGameSelections.stream().anyMatch(s -> s.getWinningTeamSelection().equals(team));
    }

    private boolean isSelectionTooLate(WeeklyGameSelection weeklyGameSelection) throws GameWillStartSoonException {
        Instant now = dateTimeService.getCurrentDateTime();
        now = now.plus(15, ChronoUnit.MINUTES);
        Instant gameTime = weeklyGameSelection.getSelectedGame().getStartTime();
        return now.isAfter(gameTime);
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

    @Override
    public WeeklyGameSelection changeTeamForSelection(Long selectionId, Long teamId) throws GameWillStartSoonException, TeamAlreadySelectedException {
        Optional<WeeklyGameSelection> maybeExistingSelection = weeklyGameSelectionRepository.findById(selectionId);
        if(!maybeExistingSelection.isPresent()) {
            throw new RuntimeException("Selection with ID " + selectionId + " not found");
        }
        WeeklyGameSelection selectionToModify = maybeExistingSelection.get();
        Optional<Team> maybeExistingTeam = teamRepository.findById(teamId);
        if (!maybeExistingTeam.isPresent()) {
            throw new RuntimeException(("Team with ID " + teamId + " not found"));
        }
        if (isSelectionTooLate(selectionToModify)) {
            throw new GameWillStartSoonException("Game start time is too close to current time");
        }
        Team replacementPick = maybeExistingTeam.get();
        if (isTeamAlreadyPicked(replacementPick, selectionToModify.getUser())) {
            throw new TeamAlreadySelectedException("Team " + replacementPick.getName() + " was already selected by this user");
        }
        selectionToModify.setTeam(replacementPick);
        return selectionToModify;
        }


}
