package com.webber.nflsurvivor.repository;

import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyGameSelectionRepository extends JpaRepository<WeeklyGameSelection, Long> {
}
