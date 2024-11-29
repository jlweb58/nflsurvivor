package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.domain.Team;

public interface TeamService {

    Team create(Team unpersistedTeam);

    Team findByName(String name);
}
