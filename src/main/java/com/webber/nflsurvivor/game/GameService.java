package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.game.Game;

import java.util.List;

public interface GameService {

    Game create(Game game);

    List<Game> findGamesForWeek(int week);
}
