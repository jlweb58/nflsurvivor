package com.webber.nflsurvivor.service.impl;


import com.webber.nflsurvivor.SurvivorApplication;
import com.webber.nflsurvivor.domain.Stadium;
import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import com.webber.nflsurvivor.game.Game;
import com.webber.nflsurvivor.game.GameService;
import com.webber.nflsurvivor.repository.StadiumRepository;
import com.webber.nflsurvivor.service.TeamService;
import com.webber.nflsurvivor.service.WeeklyGameSelectionService;
import com.webber.nflsurvivor.user.PlayerStatus;
import com.webber.nflsurvivor.user.User;
import com.webber.nflsurvivor.user.UserService;
import com.webber.nflsurvivor.util.DateTimeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SurvivorApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlayerStatusServiceImplIntegrationTest {

    @Autowired
    private PlayerStatusServiceImpl playerStatusService;

    @Autowired
    private UserService userService;

    @Autowired
    private WeeklyGameSelectionService weeklyGameSelectionService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameService gameService;

    @Autowired
    private StadiumRepository stadiumRepository;

    private User user;

    private Team team1;
    private Team team2;
    private Team team3;
    private Team team4;

    private Game game1;
    private Game game2;
    private Game game3;



    @BeforeEach
    public void setUp() throws Exception{
        user = userService.create(new User("User", "user@user.com", "pw"));
        user.setPlayerStatus(PlayerStatus.ACTIVE);
        team1 = teamService.create(new Team("Team 1", "t1"));
        team2 = teamService.create(new Team("Team 2", "t2"));
        team3 = teamService.create(new Team("Team 3", "t3"));
        team4 = teamService.create(new Team("Team 4", "t4"));
        Stadium stadium = stadiumRepository.save(new Stadium(1234L, "Stadium", "Europe/Berlin"));
        game1 = gameService.create(new Game(team1, team2, 1, Instant.now().plus(5, ChronoUnit.DAYS)).setVenue(stadium).setYear(2025));
        game2 = gameService.create(new Game(team2, team3, 2, Instant.now().plus(5, ChronoUnit.DAYS)).setVenue(stadium).setYear(2025));
        game3 = gameService.create(new Game(team3, team4, 3, Instant.now().plus(5, ChronoUnit.DAYS)).setVenue(stadium).setYear(2025));

    }

    @Test
    public void testSetsUserToEliminatedOnSecondLoss() throws Exception {
        //win
        WeeklyGameSelection selection1 = weeklyGameSelectionService.create(new WeeklyGameSelection(user, team1, game1));
        game1.setAwayPoints(0).setHomePoints(10).setFinished(true);
        playerStatusService.updateGlobalPlayerStatus();
        assertEquals(PlayerStatus.ACTIVE, user.getPlayerStatus());
        //loss
        WeeklyGameSelection selection2 = weeklyGameSelectionService.create(new WeeklyGameSelection(user, team2, game2));
        game2.setAwayPoints(10).setHomePoints(0).setFinished(true);
        playerStatusService.updateGlobalPlayerStatus();
        assertEquals(PlayerStatus.ACTIVE, user.getPlayerStatus());
        //loss
        WeeklyGameSelection selection3 = weeklyGameSelectionService.create(new WeeklyGameSelection(user, team3, game3));
        game3.setAwayPoints(20).setHomePoints(10).setFinished(true);
        playerStatusService.updateGlobalPlayerStatus();
        assertEquals(PlayerStatus.ELIMINATED, user.getPlayerStatus());
        assertTrue(user.isEliminated());

    }



}
