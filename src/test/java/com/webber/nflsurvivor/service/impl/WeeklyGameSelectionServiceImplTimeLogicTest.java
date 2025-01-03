package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.*;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WeeklyGameSelectionServiceImplTimeLogicTest extends AbstractWeeklyGameSelectionServiceImplTest {


    @Test
    public void testCreateAndFindForUser() throws Exception {
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2024, 10, 10, 9, 0), berlinZone).toInstant());
        Game selectedGame = new Game(team1, team2, 1, Instant.now()).setYear(2024).setVenue(stadium);

        selectedGame = gameService.create(selectedGame);
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
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2024, 10, 10, 9, 0), berlinZone).toInstant());
        Game selectedGame1 = new Game(team1, team2, 1, Instant.now()).setYear(2024).setVenue(stadium);
        Game selectedGame2 = new Game(team3, team4, 1, Instant.now()).setYear(2024).setVenue(stadium);
        Game selectedGame3 = new Game(team2, team3, 2, Instant.now()).setYear(2024).setVenue(stadium);
        gameService.create(selectedGame1);
        gameService.create(selectedGame2);
        gameService.create(selectedGame3);
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
    public void testCanNotCreateSelection14MinutesBeforeGameHasStarted() throws Exception {
        ZonedDateTime gameStartTime = ZonedDateTime.of(LocalDateTime.of(2025, 10, 10, 10, 0), newYorkZone);
        ZonedDateTime userCurrentTime = convertGameTimeWithOffset(gameStartTime, -14);
        WeeklyGameSelection invalidGameSelection = createWeeklyGameSelection(gameStartTime, userCurrentTime);
        assertThrows(GameWillStartSoonException.class, ()-> {
            weeklyGameSelectionService.create(invalidGameSelection);
        });
    }

    @Test
    public void testCanCreateSelection16MinutesBeforeGameHasStarted() throws Exception {
        ZonedDateTime gameStartTime = ZonedDateTime.of(LocalDateTime.of(2025, 10, 10, 10, 0), newYorkZone);
        ZonedDateTime userCurrentTime = convertGameTimeWithOffset(gameStartTime, -16);
        WeeklyGameSelection invalidGameSelection = createWeeklyGameSelection(gameStartTime, userCurrentTime);
        WeeklyGameSelection createdGameSelection = weeklyGameSelectionService.create(invalidGameSelection);
        assertNotNull(createdGameSelection);
    }

    @Test
    public void testCanCreateSelection15MinutesBeforeGameHasStarted() throws Exception {
        ZonedDateTime gameStartTime = ZonedDateTime.of(LocalDateTime.of(2025, 10, 10, 10, 0), newYorkZone);
        ZonedDateTime userCurrentTime = convertGameTimeWithOffset(gameStartTime, -15);
        WeeklyGameSelection invalidGameSelection = createWeeklyGameSelection(gameStartTime, userCurrentTime);
        WeeklyGameSelection createdGameSelection = weeklyGameSelectionService.create(invalidGameSelection);
        assertNotNull(createdGameSelection);
    }

    @Test
    public void testCanNotCreateSelectionAfterGameHasStarted() throws Exception {
        ZonedDateTime gameStartTime = ZonedDateTime.of(LocalDateTime.of(2025, 10, 10, 10, 0), newYorkZone);
        ZonedDateTime userCurrentTime = convertGameTimeWithOffset(gameStartTime, 1);
        WeeklyGameSelection invalidGameSelection = createWeeklyGameSelection(gameStartTime, userCurrentTime);
        assertThrows(GameWillStartSoonException.class, ()-> {
            weeklyGameSelectionService.create(invalidGameSelection);
        });
    }
}
