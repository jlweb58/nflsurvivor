package com.webber.nflsurvivor.season;

public interface SeasonWeekService {

    /**
     * Determines what the currently active week of the season is, using the
     * current time to compare. If the current time does not fall within a season,
     * will return 0
     * @return the current season week, or 0 if no season is running
     */
    Integer getActiveGameWeek();
}
