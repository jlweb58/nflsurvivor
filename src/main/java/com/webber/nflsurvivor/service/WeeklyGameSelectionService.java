package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.domain.User;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;

import java.util.List;

public interface WeeklyGameSelectionService {

    WeeklyGameSelection create(WeeklyGameSelection weeklyGameSelection);

    List<WeeklyGameSelection> findForUser(User user);

    List<WeeklyGameSelection> findAllForWeek(int week);
}
