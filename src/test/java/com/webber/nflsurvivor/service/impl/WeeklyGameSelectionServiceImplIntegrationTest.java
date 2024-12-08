package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.SurvivorApplication;
import com.webber.nflsurvivor.domain.*;
import com.webber.nflsurvivor.repository.WeeklyGameSelectionRepository;
import com.webber.nflsurvivor.service.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = SurvivorApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
public class WeeklyGameSelectionServiceImplIntegrationTest {


    private WeeklyGameSelectionServiceImpl weeklyGameSelectionService;

    @Autowired
    private WeeklyGameSelectionRepository weeklyGameSelectionRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @Mock
    private DateTimeService dateTimeService;

    private User user1;

    private User user2;

    private Team team1;

    private Team team2;

    private Team team3;

    private Team team4;

    private final ZoneId berlinZone = ZoneId.of("Europe/Berlin");


    @BeforeEach
    public void setUp() {
        user1 = userService.create(new User("test", "test@test.com", "12345"));
        user2 = userService.create(new User("test2", "test2@test.com", "56789"));
        team1 = teamService.create(new Team("Team1", new byte[] {1, 2, 3, 4, 5}));
        team2 = teamService.create(new Team("Team2", new byte[] {1, 2, 3, 4, 5}));
        team3 = teamService.create(new Team("Team3", new byte[] {1, 2, 3, 4, 5}));
        team4 = teamService.create(new Team("Team4", new byte[] {1, 2, 3, 4, 5}));
        weeklyGameSelectionService = new WeeklyGameSelectionServiceImpl(weeklyGameSelectionRepository, dateTimeService);
    }

    @Test
    public void testCreateAndFindForUser() throws Exception {
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2025, 10, 10, 9, 0), berlinZone));
        Game selectedGame = gameService.create(new Game(team1, team2, 1, ZonedDateTime.now()));
        WeeklyGameSelection weeklyGameSelection = new WeeklyGameSelection(user1, team1, selectedGame);
        weeklyGameSelection = weeklyGameSelectionService.create(weeklyGameSelection);
        assertNotNull(weeklyGameSelection);
        List<WeeklyGameSelection> foundGameSelections = weeklyGameSelectionService.findForUser(user1);
        assertNotNull(foundGameSelections);
        assertEquals(1, foundGameSelections.size());
        assertTrue(foundGameSelections.contains(weeklyGameSelection));
    }

    @Test
    public void testFindForWeek()throws Exception {
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2025, 10, 10, 9, 0), berlinZone));
        Game selectedGame1 = gameService.create(new Game(team1, team2, 1, ZonedDateTime.now()));
        Game selectedGame2 = gameService.create(new Game(team3, team4, 1, ZonedDateTime.now()));
        Game selectedGame3 = gameService.create(new Game(team2, team3, 2, ZonedDateTime.now()));
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

    @Test
    public void testCanNotCreateSelectionAfterGameHasStarted() throws Exception {
        LocalDateTime localGameStartTime = LocalDateTime.of(2025, 10, 10, 10, 0);
        ZoneId newYorkZone = ZoneId.of("America/New_York");
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2025, 10, 10, 9, 46), berlinZone));
        ZonedDateTime timeInNewYorkAtGameStart = ZonedDateTime.of(localGameStartTime, newYorkZone);
        LocalDateTime berlinResult = timeInNewYorkAtGameStart.withZoneSameInstant(berlinZone).toLocalDateTime();
        Game selectedGame1 = gameService.create(new Game(team1, team2, 1, ZonedDateTime.of(localGameStartTime, newYorkZone)));
        WeeklyGameSelection invalidGameSelection = new WeeklyGameSelection(user1, team1, selectedGame1);
        assertThrows(GameWillStartSoonException.class, ()-> {
            weeklyGameSelectionService.create(invalidGameSelection);
        });

    }
}
