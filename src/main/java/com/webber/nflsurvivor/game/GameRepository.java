package com.webber.nflsurvivor.repository;

import com.webber.nflsurvivor.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findGamesByWeek(int week);

    Game findGameByEspnId(long espnId);

    @Query("SELECT g from Game g WHERE (g.awayTeam.id = ?1 OR g.homeTeam.id = ?1) AND g.week <= ?2")
    Set<Game> findGamesByTeamToWeek(long teamId, long week);
}
