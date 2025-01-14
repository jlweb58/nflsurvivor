package com.webber.nflsurvivor.game;

import java.util.List;

public interface GameService {

    Game create(Game game);

    List<Game> findGamesForWeek(int week);
}
