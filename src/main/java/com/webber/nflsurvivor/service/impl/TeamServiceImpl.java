package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.repository.TeamRepository;
import com.webber.nflsurvivor.service.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team create(Team unpersistedTeam) {
        if (unpersistedTeam.getId() != null) {
            throw new IllegalArgumentException("Team " + unpersistedTeam.getName() + " already exists");
        }
        return teamRepository.save(unpersistedTeam);
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }
}
