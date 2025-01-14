package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.Game;
import com.webber.nflsurvivor.domain.GameResult;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


public class WeeklyGameSelectionServiceImplGameResultIntegrationTest extends AbstractWeeklyGameSelectionServiceImplTest {

    @Test
    public void testCorrectlySetsGameResult() throws Exception {
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2024, 10, 10, 9, 0), berlinZone).toInstant());
        Game selectedGame1 = new Game(team1, team2, 1, Instant.now());
        Game selectedGame2 = new Game(team2, team3, 2, Instant.now());
        selectedGame1.setYear(2024).setVenue(stadium).setHomePoints(30).setAwayPoints(0).setFinished(true);
        selectedGame2.setYear(2024).setVenue(stadium).setHomePoints(30).setAwayPoints(0).setFinished(true);
        gameService.create(selectedGame1);
        gameService.create(selectedGame2);
        //Winning selection
        WeeklyGameSelection weeklyGameSelection1 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team1, selectedGame1));
        //Losing selection
        WeeklyGameSelection weeklyGameSelection2 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team3, selectedGame2));
        List<WeeklyGameSelection> userSelections = weeklyGameSelectionService.findForUser(user1).stream().sorted(Comparator.comparingInt(WeeklyGameSelection::getWeek)).collect(Collectors.toList());
        assertNotNull(userSelections);
        assertEquals(GameResult.WIN, userSelections.get(0).getGameResult());
        assertEquals(GameResult.LOSS, userSelections.get(1).getGameResult());
    }


}
