package com.webber.nflsurvivor.repository;

import com.webber.nflsurvivor.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
