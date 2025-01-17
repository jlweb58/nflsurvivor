package com.webber.nflsurvivor.season;

import com.webber.nflsurvivor.SurvivorApplication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SurvivorApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class SeasonWeekRepositoryIntegrationTest {

    @Autowired
    private SeasonWeekRepository repository;

    private SeasonWeek seasonWeek1;
    private SeasonWeek seasonWeek2;
    private SeasonWeek seasonWeek3;
    private SeasonWeek seasonWeek4;

    @BeforeEach
    public void setUp() {
        seasonWeek1 = new SeasonWeek(2024, 1, Instant.parse("2024-09-05T07:00:00.00Z"), Instant.parse("2024-09-11T06:59:00.00Z"));
        seasonWeek2 = new SeasonWeek(2024, 2, Instant.parse("2024-09-11T07:00:00.00Z"), Instant.parse("2024-09-18T06:59:00.00Z"));
        seasonWeek3 = new SeasonWeek(2024, 3, Instant.parse("2024-09-18T07:00:00.00Z"), Instant.parse("2024-09-25T06:59:00.00Z"));
        seasonWeek4 = new SeasonWeek(2024, 4, Instant.parse("2024-09-25T07:00:00.00Z"), Instant.parse("2024-10-02T06:59:00.00Z"));
        repository.save(seasonWeek1);
        repository.save(seasonWeek2);
        repository.save(seasonWeek3);
        repository.save(seasonWeek4);
    }

    @Test
    public void shouldFindCorrectSeasonWeekForInstantBetween() {
        Instant now = Instant.parse("2024-09-08T07:00:00.00Z");
        assertEquals(1, repository.findByInstantBetweenStartAndEnd(now).getWeekNumber());
    }

    @Test
    public void shouldFindCorrectSeasonWeekForInstantEqualToStart() {
        Instant now = seasonWeek1.getStartDateTime();
        assertEquals(1, repository.findByInstantBetweenStartAndEnd(now).getWeekNumber());
    }

    @Test
    public void shouldFindCorrectSeasonWeekForInstantEqualToEnd() {
        Instant now = seasonWeek1.getEndDateTime();
        assertEquals(1, repository.findByInstantBetweenStartAndEnd(now).getWeekNumber());
    }

    @Test
    public void shouldFindCorrectSeasonWeekForWeek2() {
        Instant now = Instant.parse("2024-09-12T07:00:00.00Z");
        assertEquals(2, repository.findByInstantBetweenStartAndEnd(now).getWeekNumber());
    }

    @Test
    public void shouldReturnNullForInstantBeforeAllRanges() {
        Instant now = Instant.parse("2024-08-08T07:00:00.00Z");
        assertNull(repository.findByInstantBetweenStartAndEnd(now));
    }

    @Test
    public void shouldReturnNullForInstantAfterAllRanges() {
        Instant now = Instant.parse("2025-08-08T07:00:00.00Z");
        assertNull(repository.findByInstantBetweenStartAndEnd(now));
    }
}
