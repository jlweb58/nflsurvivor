package com.webber.nflsurvivor.game;


import com.webber.nflsurvivor.domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Team homeTeam;
    private Team awayTeam;

    @BeforeEach
    public void setUp() {
        homeTeam = new Team("Home", "Home");
        awayTeam = new Team("Away", "Away");

    }

    @Test
    public void testGetWinningTeamHomeTeam() {
        Game game = new Game(homeTeam, awayTeam, 1, Instant.now());
        game.setHomePoints(30).setAwayPoints(0).setFinished(true);
        assertEquals(homeTeam, game.getWinningTeam());
    }

    @Test
    public void testGetWinningTeamAwayTeam() {
        Game game = new Game(homeTeam, awayTeam, 1, Instant.now());
        game.setHomePoints(0).setAwayPoints(30).setFinished(true);
        assertEquals(awayTeam, game.getWinningTeam());
    }

    @Test
    public void testGetWinningTeamTied() {
        Game game = new Game(homeTeam, awayTeam, 1, Instant.now());
        game.setHomePoints(0).setAwayPoints(0).setFinished(true);
        assertNull(game.getWinningTeam());
    }

    @Test
    public void testGetWinningTeamNotFinished() {
        Game game = new Game(homeTeam, awayTeam, 1, Instant.now());
        game.setFinished(false);
        assertNull(game.getWinningTeam());
    }

}
