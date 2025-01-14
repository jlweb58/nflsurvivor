package com.webber.nflsurvivor.game;

import com.webber.nflsurvivor.SurvivorApplication;
import com.webber.nflsurvivor.domain.Stadium;
import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.repository.StadiumRepository;
import com.webber.nflsurvivor.service.TeamService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
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

    @Autowired
    private StadiumRepository stadiumRepository;

    @Test
    public void testCreateGame() {
        Stadium stadium = new Stadium(1234L, "Doghouse Stadium", "America/New_York");
        stadiumRepository.save(stadium);
        Team homeTeam = createTeam("Home");
        Team awayTeam = createTeam("Away");
        Game game = new Game(homeTeam, awayTeam, 1, Instant.now());
        game.setYear(2024);
        game.setVenue(stadium);
        Game created = gameService.create(game);
        assertNotNull(created);
    }

    @Test
    public void testFindGamesForWeek() {
        Stadium stadium = new Stadium(1234L, "Doghouse Stadium", "America/New_York");
        stadiumRepository.save(stadium);
        //Create 3 games, 2 for the same week. Load the games for that week, see that they are correct
        Team homeTeam1 = createTeam("Home1");
        Team awayTeam1 = createTeam("Away1");
        Team homeTeam2 = createTeam("Home2");
        Team awayTeam2 = createTeam("Away2");
        Team homeTeam3 = createTeam("Home3");
        Team awayTeam3 = createTeam("Away3");
        Game game1 = new Game(homeTeam1, awayTeam1, 1, Instant.now());
        Game game2 = new Game(homeTeam2, awayTeam2, 1, Instant.now());
        Game game3 = new Game(homeTeam3, awayTeam3, 2, Instant.now());
        game1.setFinished(false);
        game2.setFinished(false);
        game3.setFinished(false);
        game1.setYear(2024).setEspnId(1L);
        game2.setYear(2024).setEspnId(2L);
        game3.setYear(2024).setEspnId(3L);
        game1.setVenue(stadium);
        game2.setVenue(stadium);
        game3.setVenue(stadium);
        gameService.create(game1);
        gameService.create(game2);
        gameService.create(game3);
        List<Game> gamesForWeek1 = gameService.findGamesForWeek(1);
        assertTrue(gamesForWeek1.contains(game1));
        assertTrue(gamesForWeek1.contains(game2));
        assertFalse(gamesForWeek1.contains(game3));
        List<Game> gamesForWeek2 = gameService.findGamesForWeek(2);
        assertEquals(1, gamesForWeek2.size());
        assertTrue(gamesForWeek2.contains(game3));
    }

    @Test
    public void testSaveWithStadium() {
        Stadium stadium = new Stadium(1234L, "Doghouse Stadium", "America/New_York");
        stadiumRepository.save(stadium);
        Team homeTeam1 = createTeam("Home1");
        Team awayTeam1 = createTeam("Away1");
        Game game1 = new Game(homeTeam1, awayTeam1, 1, Instant.now());
        game1.setYear(2024).setEspnId(1L);
        game1.setVenue(stadium);
        gameService.create(game1);
        assertNotNull(game1);
        assertEquals(stadium, game1.getVenue());
    }

    private Team createTeam(String name) {
        return teamService.create(new Team(name, name.substring(0, 3)));
    }
}
