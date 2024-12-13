package com.webber.nflsurvivor.controller;

import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    public List<Team> getTeams() {
        return teamService.findAll();
    }

}
