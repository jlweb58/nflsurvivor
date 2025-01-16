package com.webber.nflsurvivor.controller;

import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.domain.WeeklyTeamScore;
import com.webber.nflsurvivor.service.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }


    @GetMapping
    public List<Team> getTeams() {
        return teamService.findAll();
    }

    @GetMapping(path="/{teamId}")
    public ResponseEntity<WeeklyTeamScore> getWeeklyTeamScore(@PathVariable Long teamId, @RequestParam int week) {
        return ResponseEntity.ok(teamService.getWeeklyTeamScoreByTeamId(teamId, week));
    }

}
