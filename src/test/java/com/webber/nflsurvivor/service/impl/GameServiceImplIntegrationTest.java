package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.SurvivorApplication;
import com.webber.nflsurvivor.domain.Game;
import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.service.GameService;
import com.webber.nflsurvivor.service.TeamService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SurvivorApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class GameServiceImplIntegrationTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private TeamService teamService;

    @Test
    public void testCreateGame() {
        Team homeTeam = createTeam("Home");
        Team awayTeam = createTeam("Away");
        Integer week = 1;
        ZonedDateTime gameStartTime = ZonedDateTime.now();
        Game game = new Game(homeTeam, awayTeam, week, gameStartTime);
        Game created = gameService.create(game);
        assertNotNull(created);
    }

    @Test
    public void testFindGamesForWeek() {
        //Create 3 games, 2 for the same week. Load the games for that week, see that they are correct
        Team homeTeam1 = createTeam("Home1");
        Team awayTeam1 = createTeam("Away1");
        Team homeTeam2 = createTeam("Home2");
        Team awayTeam2 = createTeam("Away2");
        Team homeTeam3 = createTeam("Home3");
        Team awayTeam3 = createTeam("Away3");
        Game game1 = new Game(homeTeam1, awayTeam1, 1, ZonedDateTime.now());
        Game game2 = new Game(homeTeam2, awayTeam2, 1, ZonedDateTime.now());
        Game game3 = new Game(homeTeam3, awayTeam3, 2, ZonedDateTime.now());
        Game createdGame1 = gameService.create(game1);
        Game createdGame2 = gameService.create(game2);
        Game createdGame3 = gameService.create(game3);
        List<Game> gamesForWeek1 = gameService.findGamesForWeek(1);
        assertTrue(gamesForWeek1.contains(game1));
        assertTrue(gamesForWeek1.contains(game2));
        assertFalse(gamesForWeek1.contains(game3));
        List<Game> gamesForWeek2 = gameService.findGamesForWeek(2);
        assertEquals(1, gamesForWeek2.size());
        assertTrue(gamesForWeek2.contains(createdGame3));
    }

    private Team createTeam(String name) {
        return teamService.create(new Team(name, name.substring(0, 3)));
    }
}
