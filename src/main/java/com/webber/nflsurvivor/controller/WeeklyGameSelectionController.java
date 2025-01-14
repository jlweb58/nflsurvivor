package com.webber.nflsurvivor.controller;

import com.webber.nflsurvivor.game.Game;
import com.webber.nflsurvivor.domain.GameWillStartSoonException;
import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.domain.TeamAlreadySelectedException;
import com.webber.nflsurvivor.user.User;
import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import com.webber.nflsurvivor.game.GameRepository;
import com.webber.nflsurvivor.repository.TeamRepository;
import com.webber.nflsurvivor.user.UserService;
import com.webber.nflsurvivor.service.WeeklyGameSelectionService;
import com.webber.nflsurvivor.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> createWeeklyGameSelection(@PathVariable Long gameId, @PathVariable Long teamId)  {
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
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Successfully created selection"));
    }

    @GetMapping
    public ResponseEntity<List<WeeklyGameSelection>> getAllForCurrentUser() throws UserNotFoundException {
        User user = userService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.OK).body(weeklyGameSelectionService.findForUser(user));
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<List<WeeklyGameSelection>> getAllForUser(@PathVariable Long userId) throws UserNotFoundException {
        User user = userService.findUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(weeklyGameSelectionService.findForUser(user));
    }

}
