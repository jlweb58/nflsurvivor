package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.domain.WeeklyTeamScore;

import java.util.List;

public interface TeamService {

    Team create(Team unpersistedTeam);

    Team findByName(String name);

    List<Team> findAll();

    /**
     * Calculates the W-L-T result for a team up to and including the given week. If the games of the given week have not yet been played,
     * they will not count. The result would be suitable for display in a UI, e.g. "4-2-1"
     * @param teamId  The id of the team
     * @param week The upper limit of the week numbers - all weeks up to that week will be used for the calculation
     * @return  The WeeklyTeamScore for that team and week.
     */
    WeeklyTeamScore getWeeklyTeamScoreByTeamId(Long teamId, int week);
}
