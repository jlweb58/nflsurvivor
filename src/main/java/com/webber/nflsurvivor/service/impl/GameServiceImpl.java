package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.Game;
import com.webber.nflsurvivor.repository.GameRepository;
import com.webber.nflsurvivor.service.GameService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
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
        return gameRepository.findGamesByWeek(week);
    }
}
