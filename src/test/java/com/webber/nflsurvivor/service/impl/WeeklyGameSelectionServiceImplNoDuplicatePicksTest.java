package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.Game;
import com.webber.nflsurvivor.domain.TeamAlreadySelectedException;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class WeeklyGameSelectionServiceImplNoDuplicatePicksTest extends AbstractWeeklyGameSelectionServiceImplTest {


    @Test
    public void testCannotCreateTwoSelectionsWithSameWinner() throws Exception {
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2024, 10, 10, 9, 0), berlinZone));
        Game selectedGame1 = gameService.create(new Game(team1, team2, 1, ZonedDateTime.now()));
        Game selectedGame2 = gameService.create(new Game(team2, team3, 2, ZonedDateTime.now()));
        WeeklyGameSelection weeklyGameSelection1 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team2, selectedGame1));
        assertThrows(TeamAlreadySelectedException.class, ()-> {
            WeeklyGameSelection weeklyGameSelection2 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team2, selectedGame2));
        });
    }

    @Test
    public void testCanCreateTwoSelectionsWithDifferentWinners() throws Exception {
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2024, 10, 10, 9, 0), berlinZone));
        Game selectedGame1 = gameService.create(new Game(team1, team2, 1, ZonedDateTime.now()));
        Game selectedGame2 = gameService.create(new Game(team2, team3, 2, ZonedDateTime.now()));
        WeeklyGameSelection weeklyGameSelection1 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team2, selectedGame1));
        WeeklyGameSelection weeklyGameSelection2 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team3, selectedGame2));
        assertNotNull(weeklyGameSelection1);
        assertNotNull(weeklyGameSelection2);
    }

    @Test
    public void testCanCreateThreeSelectionsWithDifferentWinners() throws Exception {
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2024, 10, 10, 9, 0), berlinZone));
        Game selectedGame1 = gameService.create(new Game(team1, team2, 1, ZonedDateTime.now()));
        Game selectedGame2 = gameService.create(new Game(team2, team3, 2, ZonedDateTime.now()));
        Game selectedGame3 = gameService.create(new Game(team3, team4, 3, ZonedDateTime.now()));
        WeeklyGameSelection weeklyGameSelection1 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team2, selectedGame1));
        WeeklyGameSelection weeklyGameSelection2 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team3, selectedGame2));
        WeeklyGameSelection weeklyGameSelection3 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team4, selectedGame3));
        assertNotNull(weeklyGameSelection1);
        assertNotNull(weeklyGameSelection2);
        assertNotNull(weeklyGameSelection3);
    }

}
