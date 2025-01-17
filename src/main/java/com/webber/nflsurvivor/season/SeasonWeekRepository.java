package com.webber.nflsurvivor.season;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

public interface SeasonWeekRepository extends JpaRepository<SeasonWeek, Long> {

    @Query("SELECT w FROM SeasonWeek w WHERE w.startDateTime <= ?1 and w.endDateTime >= ?1")
    SeasonWeek findByInstantBetweenStartAndEnd(Instant dateToCompare);
}
