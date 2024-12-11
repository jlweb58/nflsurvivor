package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.Game;
import com.webber.nflsurvivor.domain.TeamAlreadySelectedException;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class WeeklyGameSelectionServiceImplNoDuplicatePicksTest extends AbstractWeeklyGameSelectionServiceImplTest {


    @Test
    public void testCanCreateTwoSelectionsWithDifferentWinners() throws Exception {
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2024, 10, 10, 9, 0), berlinZone));
        Game selectedGame1 = gameService.create(new Game(team1, team2, 1, ZonedDateTime.now()));
        Game selectedGame2 = gameService.create(new Game(team2, team3, 2, ZonedDateTime.now()));
        WeeklyGameSelection weeklyGameSelection1 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team2, selectedGame1));
        assertThrows(TeamAlreadySelectedException.class, ()-> {
            WeeklyGameSelection weeklyGameSelection2 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team2, selectedGame2));
        });
    }
}
