package com.webber.nflsurvivor.season;

import com.webber.nflsurvivor.game.Game;

import java.util.Set;

public interface ScheduleImportService {


    Set<Game> importScheduleForWeekAndYear(Integer week, Integer year);

    void importScheduleWeeksForYear(Integer year);

}
