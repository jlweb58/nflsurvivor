package com.webber.nflsurvivor.repository;

import com.webber.nflsurvivor.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
