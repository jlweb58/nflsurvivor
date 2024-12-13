package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.domain.Team;

import java.util.List;

public interface TeamService {

    Team create(Team unpersistedTeam);

    Team findByName(String name);

    List<Team> findAll();
}
