package com.webber.nflsurvivor.repository;

import com.webber.nflsurvivor.domain.User;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeeklyGameSelectionRepository extends JpaRepository<WeeklyGameSelection, Long> {

    List<WeeklyGameSelection> findAllByUser(User user);

    @Query("SELECT s FROM WeeklyGameSelection s WHERE s.selectedGame.week = ?1")
    List<WeeklyGameSelection> findAllByWeek(int week);
}
