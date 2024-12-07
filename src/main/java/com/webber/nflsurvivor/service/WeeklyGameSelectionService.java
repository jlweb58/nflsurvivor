package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.domain.GameWillStartSoonException;
import com.webber.nflsurvivor.domain.TeamAlreadySelectedException;
import com.webber.nflsurvivor.domain.User;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;

import java.time.ZonedDateTime;
import java.util.List;

public interface WeeklyGameSelectionService {

    /**
     * Attempts to set a new weekly game selection for a user.
     *
     * @param weeklyGameSelection a game selection for a given user
     * @return A valid WeeklyGameSelection object
     * @throws GameWillStartSoonException  if the starting time of the game is in the past, or is less than the configured minimum number of minutes for a selection
     * @throws TeamAlreadySelectedException if the user has already used the picked team in a past week
     */
    WeeklyGameSelection create(WeeklyGameSelection weeklyGameSelection) throws GameWillStartSoonException, TeamAlreadySelectedException;

    List<WeeklyGameSelection> findForUser(User user);

    List<WeeklyGameSelection> findAllForWeek(int week);
}
