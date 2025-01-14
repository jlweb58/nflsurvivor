package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.Game;
import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.domain.WeeklyTeamScore;
import com.webber.nflsurvivor.repository.GameRepository;
import com.webber.nflsurvivor.repository.TeamRepository;
import com.webber.nflsurvivor.service.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, GameRepository gameRepository) {
        this.teamRepository = teamRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Team create(Team unpersistedTeam) {
        if (unpersistedTeam.getId() != null) {
            throw new IllegalArgumentException("Team " + unpersistedTeam.getName() + " already exists");
        }
        return teamRepository.save(unpersistedTeam);
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public WeeklyTeamScore getWeeklyTeamScoreByTeamId(Long teamId, int week) {
        Set<Game> teamGamesToWeek = gameRepository.findGamesByTeamToWeek(teamId, week);
        WeeklyTeamScore weeklyTeamScore = new WeeklyTeamScore();
        for (Game game: teamGamesToWeek) {
            if (!game.isFinished()) {
                continue;
            }
            Team winningTeam = game.getWinningTeam();
            incrementResultCount(teamId, winningTeam, weeklyTeamScore);
        }

        return weeklyTeamScore;
    }

    private static void incrementResultCount(Long teamId, Team winningTeam, WeeklyTeamScore weeklyTeamScore) {
        if (winningTeam == null) {
            weeklyTeamScore.incrementTieCount();
        } else if (winningTeam.getId().equals(teamId)) {
            weeklyTeamScore.incrementWinCount();
        } else {
            weeklyTeamScore.incrementLossCount();
        }
    }

}
