package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.SurvivorApplication;
import com.webber.nflsurvivor.domain.Game;
import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.domain.User;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SurvivorApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class WeeklyGameSelectionServiceImplIntegrationTest {

    @Autowired
    private WeeklyGameSelectionService weeklyGameSelectionService;

    @Autowired
    private GameService gameService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    private User user1;

    private User user2;

    private Team team1;

    private Team team2;

    private Team team3;

    private Team team4;

    @BeforeEach
    public void setUp() {
        user1 = userService.create(new User("test", "test@test.com", "12345"));
        user2 = userService.create(new User("test2", "test2@test.com", "56789"));
        team1 = teamService.create(new Team("Team1", new byte[] {1, 2, 3, 4, 5}));
        team2 = teamService.create(new Team("Team2", new byte[] {1, 2, 3, 4, 5}));
        team3 = teamService.create(new Team("Team3", new byte[] {1, 2, 3, 4, 5}));
        team4 = teamService.create(new Team("Team4", new byte[] {1, 2, 3, 4, 5}));
    }

    @Test
    public void testCreateAndFindForUser() {
        Game selectedGame = gameService.create(new Game(team1, team2, 1, OffsetDateTime.now()));
        WeeklyGameSelection weeklyGameSelection = new WeeklyGameSelection(user1, team1, selectedGame);
        weeklyGameSelection = weeklyGameSelectionService.create(weeklyGameSelection);
        assertNotNull(weeklyGameSelection);
        List<WeeklyGameSelection> foundGameSelections = weeklyGameSelectionService.findForUser(user1);
        assertNotNull(foundGameSelections);
        assertEquals(1, foundGameSelections.size());
        assertTrue(foundGameSelections.contains(weeklyGameSelection));
    }

    @Test
    public void testFindForWeek() {
        Game selectedGame1 = gameService.create(new Game(team1, team2, 1, OffsetDateTime.now()));
        Game selectedGame2 = gameService.create(new Game(team3, team4, 1, OffsetDateTime.now()));
        Game selectedGame3 = gameService.create(new Game(team2, team3, 2, OffsetDateTime.now()));
        WeeklyGameSelection weeklyGameSelection1 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team1, selectedGame1));
        WeeklyGameSelection weeklyGameSelection2 = weeklyGameSelectionService.create(new WeeklyGameSelection(user2, team2, selectedGame2));
        WeeklyGameSelection weeklyGameSelection3 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team2, selectedGame3));
        List<WeeklyGameSelection> selectionsForWeek1 = weeklyGameSelectionService.findAllForWeek(1);
        assertNotNull(selectionsForWeek1);
        assertEquals(2, selectionsForWeek1.size());
        assertTrue(selectionsForWeek1.contains(weeklyGameSelection1));
        assertTrue(selectionsForWeek1.contains(weeklyGameSelection2));
        List<WeeklyGameSelection> selectionsForWeek2 = weeklyGameSelectionService.findAllForWeek(2);
        assertNotNull(selectionsForWeek2);
        assertEquals(1, selectionsForWeek2.size());
        assertTrue(selectionsForWeek2.contains(weeklyGameSelection3));
    }
}
