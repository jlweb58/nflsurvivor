package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.SurvivorApplication;
import com.webber.nflsurvivor.domain.*;
import com.webber.nflsurvivor.game.Game;
import com.webber.nflsurvivor.repository.StadiumRepository;
import com.webber.nflsurvivor.repository.TeamRepository;
import com.webber.nflsurvivor.repository.WeeklyGameSelectionRepository;
import com.webber.nflsurvivor.season.SeasonWeekService;
import com.webber.nflsurvivor.util.DateTimeService;
import com.webber.nflsurvivor.game.GameService;
import com.webber.nflsurvivor.service.TeamService;
import com.webber.nflsurvivor.user.UserService;
import com.webber.nflsurvivor.user.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = SurvivorApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
public class AbstractWeeklyGameSelectionServiceImplTest {
    protected final ZoneId berlinZone = ZoneId.of("Europe/Berlin");
    protected final ZoneId newYorkZone = ZoneId.of("America/New_York");
    protected WeeklyGameSelectionServiceImpl weeklyGameSelectionService;
    @Autowired
    protected WeeklyGameSelectionRepository weeklyGameSelectionRepository;
    @Autowired
    protected TeamRepository teamRepository;
    @Autowired
    protected GameService gameService;
    @Autowired
    protected TeamService teamService;
    @Autowired
    protected UserService userService;
    @Mock
    protected DateTimeService dateTimeService;

    @Mock
    protected SeasonWeekService seasonWeekService;
    protected User user1;
    protected User user2;
    protected Team team1;
    protected Team team2;
    protected Team team3;
    protected Team team4;

    protected Stadium stadium;

    @Autowired
    private StadiumRepository stadiumRepository;

    @BeforeEach
    public void setUp() throws Exception {
        user1 = userService.create(new User("test", "test@test.com", "12345"));
        user2 = userService.create(new User("test2", "test2@test.com", "56789"));
        team1 = teamService.create(new Team("Team1", "te1"));
        team2 = teamService.create(new Team("Team2", "te2"));
        team3 = teamService.create(new Team("Team3", "te3"));
        team4 = teamService.create(new Team("Team4", "te4"));
        stadium = stadiumRepository.save(new Stadium(1234L, "Doghouse Stadium", "America/New_York"));
        weeklyGameSelectionService = new WeeklyGameSelectionServiceImpl(weeklyGameSelectionRepository, dateTimeService,
                teamService, teamRepository, seasonWeekService);
    }

    protected ZonedDateTime convertGameTimeWithOffset(ZonedDateTime gameTime, int offsetMinutes) {
        ZonedDateTime localTime = gameTime.withZoneSameInstant(berlinZone);
        return localTime.plusMinutes(offsetMinutes);
    }

    protected WeeklyGameSelection createWeeklyGameSelection(ZonedDateTime gameStartTime, ZonedDateTime userCurrentDateTime) {
        when(dateTimeService.getCurrentDateTime()).thenReturn(userCurrentDateTime.toInstant());
        Game selectedGame1 = new Game(team1, team2, 1, gameStartTime.toInstant()).setYear(2024).setVenue(stadium);
        gameService.create(selectedGame1);
        return new WeeklyGameSelection(user1, team1, selectedGame1);
    }
}
