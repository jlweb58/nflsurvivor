package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.SurvivorApplication;
import com.webber.nflsurvivor.game.Game;
import com.webber.nflsurvivor.domain.Stadium;
import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.domain.WeeklyTeamScore;
import com.webber.nflsurvivor.repository.StadiumRepository;
import com.webber.nflsurvivor.game.GameService;
import com.webber.nflsurvivor.service.TeamService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SurvivorApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class TeamServiceImplIntegrationTest {

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameService gameService;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Test
    public void testCreateAndFindByName() {
        Team team = new Team("Seattle Seahawks", "sea");
        Team created = teamService.create(team);
        assertNotNull(created);
        Team found = teamService.findByName(created.getName());
        assertEquals(created, found);
    }

    @Test
    public void testCreateAlreadyExists() {
        Team team = new Team("Seattle Seahawks", "sea");
        Team created = teamService.create(team);
        assertThrows(IllegalArgumentException.class, () -> teamService.create(created));
    }

    @Test
    public void testCalculateWeeklyTeamScore() {
        Stadium stadium = new Stadium(1234L, "Doghouse Stadium", "America/New_York");
        stadiumRepository.save(stadium);
        Team team1 = new Team("Team1", "tea1");
        Team team2 = new Team("Team2", "tea2");
        Team team3 = new Team("Team3", "tea3");
        Team team4 = new Team("Team4", "tea4");
        teamService.create(team1);
        teamService.create(team2);
        teamService.create(team3);
        teamService.create(team4);
        Game game1 = new Game(team1, team2, 1, Instant.now());
        Game game2 = new Game(team3, team4, 1, Instant.now());
        Game game3 = new Game(team2, team3, 2, Instant.now());
        Game game4 = new Game(team4, team1, 2, Instant.now());
        game1.setAwayPoints(0).setHomePoints(28).setYear(2024).setFinished(true).setVenue(stadium); //Team1 28 Team2 0
        game2.setAwayPoints(10).setHomePoints(10).setYear(2024).setFinished(true).setVenue(stadium); //Team3 10 Team4 10
        game3.setAwayPoints(21).setHomePoints(10).setYear(2024).setFinished(true).setVenue(stadium); //Team2 10 Team3 21
        game4.setAwayPoints(20).setHomePoints(30).setYear(2024).setFinished(true).setVenue(stadium); //Team4 30 Team1 20
        gameService.create(game1);
        gameService.create(game2);
        gameService.create(game3);
        gameService.create(game4);
        WeeklyTeamScore team1Week1Score = teamService.getWeeklyTeamScoreByTeamId(team1.getId(), 1);
        WeeklyTeamScore team2Week1Score = teamService.getWeeklyTeamScoreByTeamId(team2.getId(), 1);
        WeeklyTeamScore team3Week1Score = teamService.getWeeklyTeamScoreByTeamId(team3.getId(), 1);
        WeeklyTeamScore team4Week1Score = teamService.getWeeklyTeamScoreByTeamId(team4.getId(), 1);
        WeeklyTeamScore team1Week2Score = teamService.getWeeklyTeamScoreByTeamId(team1.getId(), 2);
        WeeklyTeamScore team2Week2Score = teamService.getWeeklyTeamScoreByTeamId(team2.getId(), 2);
        WeeklyTeamScore team3Week2Score = teamService.getWeeklyTeamScoreByTeamId(team3.getId(), 2);
        WeeklyTeamScore team4Week2Score = teamService.getWeeklyTeamScoreByTeamId(team4.getId(), 2);
        assertEquals(new WeeklyTeamScore(1, 0, 0), team1Week1Score);
        assertEquals(new WeeklyTeamScore(0, 1, 0), team2Week1Score);
        assertEquals(new WeeklyTeamScore(0, 0, 1), team3Week1Score);
        assertEquals(new WeeklyTeamScore(0, 0, 1), team4Week1Score);
        assertEquals(new WeeklyTeamScore(1, 1, 0), team1Week2Score);
        assertEquals(new WeeklyTeamScore(0, 2, 0), team2Week2Score);
        assertEquals(new WeeklyTeamScore(1, 0, 1), team3Week2Score);
        assertEquals(new WeeklyTeamScore(1, 0, 1), team4Week2Score);
    }

}
