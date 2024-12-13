package com.webber.nflsurvivor.controller;

import com.webber.nflsurvivor.domain.Game;
import com.webber.nflsurvivor.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{weekNumber}")
    public List<Game> getGamesForWeek(@PathVariable Integer weekNumber) {
        return gameService.findGamesForWeek(weekNumber);
    }


}
