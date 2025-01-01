package com.webber.nflsurvivor.repository;

import com.webber.nflsurvivor.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findGamesByWeek(int week);

    Game findGameByEspnId(long espnId);
}
