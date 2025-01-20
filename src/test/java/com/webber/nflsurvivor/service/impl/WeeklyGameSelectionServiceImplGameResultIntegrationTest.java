package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.WrongSeasonWeekException;
import com.webber.nflsurvivor.game.Game;
import com.webber.nflsurvivor.game.GameResult;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class WeeklyGameSelectionServiceImplGameResultIntegrationTest extends AbstractWeeklyGameSelectionServiceImplTest {

    @Test
    public void shouldCorrectlySetWinningGameResult() throws Exception {
        when(seasonWeekService.getActiveGameWeek()).thenReturn(1);
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2024, 10, 10, 9, 0), berlinZone).toInstant());
        Game selectedGame1 = new Game(team1, team2, 1, Instant.now());
        selectedGame1.setYear(2024).setVenue(stadium).setHomePoints(30).setAwayPoints(0).setFinished(true);
        gameService.create(selectedGame1);
        //Winning selection
        WeeklyGameSelection weeklyGameSelection1 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team1, selectedGame1));
        List<WeeklyGameSelection> userSelections = weeklyGameSelectionService.findForUser(user1).stream().sorted(Comparator.comparingInt(WeeklyGameSelection::getWeek)).collect(Collectors.toList());
        assertNotNull(userSelections);
        assertEquals(GameResult.WIN, userSelections.get(0).getGameResult());
    }

    @Test
    public void shouldCorrectlySetLosingGameResult() throws Exception {
        when(seasonWeekService.getActiveGameWeek()).thenReturn(2);
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2024, 10, 10, 9, 0), berlinZone).toInstant());
        Game selectedGame2 = new Game(team2, team3, 2, Instant.now());
        selectedGame2.setYear(2024).setVenue(stadium).setHomePoints(30).setAwayPoints(0).setFinished(true);
        gameService.create(selectedGame2);
        //Losing selection
        WeeklyGameSelection weeklyGameSelection2 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team3, selectedGame2));
        List<WeeklyGameSelection> userSelections = weeklyGameSelectionService.findForUser(user1).stream().sorted(Comparator.comparingInt(WeeklyGameSelection::getWeek)).collect(Collectors.toList());
        assertNotNull(userSelections);
        assertEquals(GameResult.LOSS, userSelections.get(0).getGameResult());

    }

    @Test
    public void shouldNotAllowSelectionNotInCurrentSeasonWeek() {
        when(seasonWeekService.getActiveGameWeek()).thenReturn(2);
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2024, 9, 12, 9, 0), berlinZone).toInstant());
        Game selectedGame1 = new Game(team1, team2, 3, ZonedDateTime.of(LocalDateTime.of(2024, 9, 24, 9, 0), berlinZone).toInstant());
        selectedGame1.setYear(2024).setVenue(stadium);
        gameService.create(selectedGame1);
        assertThrows(WrongSeasonWeekException.class, () -> weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team1, selectedGame1)));
    }

    @Test
    public void shouldAllowWeek1PickBeforeWeek1Starts() throws Exception {
        when(seasonWeekService.getActiveGameWeek()).thenReturn(0);
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2024, 9, 1, 9, 0), berlinZone).toInstant());
        Game selectedGame1 = new Game(team1, team2, 1, ZonedDateTime.of(LocalDateTime.of(2024, 9, 24, 9, 0), berlinZone).toInstant());
        selectedGame1.setYear(2024).setVenue(stadium);
        gameService.create(selectedGame1);
        WeeklyGameSelection weeklyGameSelection1 = weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team1, selectedGame1));
        assertNotNull(weeklyGameSelection1);
    }

    @Test
    public void shouldNotAllowWeek2PickBeforeWeek1Starts() throws Exception {
        when(seasonWeekService.getActiveGameWeek()).thenReturn(0);
        when(dateTimeService.getCurrentDateTime()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2024, 9, 1, 9, 0), berlinZone).toInstant());
        Game selectedGame1 = new Game(team1, team2, 2, ZonedDateTime.of(LocalDateTime.of(2024, 9, 24, 9, 0), berlinZone).toInstant());
        selectedGame1.setYear(2024).setVenue(stadium);
        gameService.create(selectedGame1);
        assertThrows(WrongSeasonWeekException.class, () -> weeklyGameSelectionService.create(new WeeklyGameSelection(user1, team1, selectedGame1)));
    }


}
