package com.webber.nflsurvivor.controller;

import com.webber.nflsurvivor.domain.Game;
import com.webber.nflsurvivor.service.GameService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    private final static Logger LOG = LoggerFactory.getLogger(GameController.class);

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> getGamesForYearAndWeek(@RequestParam int year, @RequestParam int week) {
        LOG.info("getGamesForYearAndWeek - " + year + " " + week);
        return gameService.findGamesForWeek(week);
    }


}
