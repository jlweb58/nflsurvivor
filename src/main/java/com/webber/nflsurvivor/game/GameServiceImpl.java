package com.webber.nflsurvivor.game;

import com.webber.nflsurvivor.domain.WeeklyTeamScore;
import com.webber.nflsurvivor.service.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final TeamService teamService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, TeamService teamService) {
        this.gameRepository = gameRepository;
        this.teamService = teamService;
    }

    @Override
    public Game create(Game game) {
        if (game.getId() != null) {
            throw new IllegalArgumentException("Game " + game + " already exists");
        }
        return gameRepository.save(game);
    }

    @Override
    public List<Game> findGamesForWeek(int week) {
        List<Game> games = gameRepository.findGamesByWeek(week);
        games.forEach(game->{
            WeeklyTeamScore awayTeamScore = teamService.getWeeklyTeamScoreByTeamId(game.getAwayTeam().getId(), week);
            WeeklyTeamScore homeTeamScore = teamService.getWeeklyTeamScoreByTeamId(game.getHomeTeam().getId(), week);
            game.getAwayTeam().setWeeklyTeamScore(awayTeamScore);
            game.getHomeTeam().setWeeklyTeamScore(homeTeamScore);
        });

        return games;
    }

    @Override
    public Integer getActiveGameWeek() {
        return 0;
    }
}
