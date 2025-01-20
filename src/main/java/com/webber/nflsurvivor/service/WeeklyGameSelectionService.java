package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.domain.GameWillStartSoonException;
import com.webber.nflsurvivor.domain.TeamAlreadySelectedException;
import com.webber.nflsurvivor.domain.WrongSeasonWeekException;
import com.webber.nflsurvivor.user.User;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;

import java.util.List;
import java.util.Optional;

public interface WeeklyGameSelectionService {

    /**
     * Attempts to set a new weekly game selection for a user.
     *
     * @param weeklyGameSelection a game selection for a given user
     * @return A valid WeeklyGameSelection object
     * @throws GameWillStartSoonException  if the starting time of the game is in the past, or is less than the configured minimum number of minutes for a selection
     * @throws TeamAlreadySelectedException if the user has already used the picked team in a past week
     */
    WeeklyGameSelection create(WeeklyGameSelection weeklyGameSelection) throws GameWillStartSoonException, TeamAlreadySelectedException, WrongSeasonWeekException;

    List<WeeklyGameSelection> findForUser(User user);

    List<WeeklyGameSelection> findAllForWeek(int week);

    /**
     * Replace the team in the given selection (subject to all usual validity checks)
     * @param selectionId  The ID of the existing selection (which must be for the current week)
     * @param teamId The ID of the team to select instead of the already existing team selection (the team must not have been already
     *               selected by the user)
     * @return  The updated selection
     * @throws GameWillStartSoonException If the selection's game is going to start in 15 minutes or less
     * @throws TeamAlreadySelectedException If the user has already selected this team in a past week.
     */
    WeeklyGameSelection changeTeamForSelection(Long selectionId, Long teamId) throws GameWillStartSoonException, TeamAlreadySelectedException;
}
