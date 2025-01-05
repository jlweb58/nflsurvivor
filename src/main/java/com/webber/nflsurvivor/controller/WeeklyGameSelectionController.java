package com.webber.nflsurvivor.controller;

import com.webber.nflsurvivor.domain.Game;
import com.webber.nflsurvivor.domain.GameWillStartSoonException;
import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.domain.TeamAlreadySelectedException;
import com.webber.nflsurvivor.domain.User;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import com.webber.nflsurvivor.repository.GameRepository;
import com.webber.nflsurvivor.repository.TeamRepository;
import com.webber.nflsurvivor.service.UserService;
import com.webber.nflsurvivor.service.WeeklyGameSelectionService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Transactional
@RequestMapping("/weeklygameselections")
public class WeeklyGameSelectionController {

    private final UserService userService;

    private final GameRepository gameRepository;

    private final TeamRepository teamRepository;

    private final WeeklyGameSelectionService weeklyGameSelectionService;

    private static final Logger LOG = LoggerFactory.getLogger(WeeklyGameSelectionController.class);

    @Autowired
    public WeeklyGameSelectionController(UserService userService, GameRepository gameRepository,
                                         TeamRepository teamRepository, WeeklyGameSelectionService weeklyGameSelectionService) {
        this.userService = userService;
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.weeklyGameSelectionService = weeklyGameSelectionService;
    }

    @PostMapping(path = "/{gameId}/{teamId}")
    public ResponseEntity<String> createWeeklyGameSelection(@PathVariable Long gameId, @PathVariable Long teamId)  {
        LOG.info("creating weekly game selection for game " + gameId + ", team " + teamId);
        User user = null;
        try {
            user = userService.getCurrentUser();
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        Game game = gameRepository.getReferenceById(gameId);
        Team team = teamRepository.getReferenceById(teamId);
        WeeklyGameSelection weeklyGameSelection = new WeeklyGameSelection(user, team, game);
        try {
            weeklyGameSelectionService.create(weeklyGameSelection);
        } catch (GameWillStartSoonException | TeamAlreadySelectedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully created selection");
    }

}
