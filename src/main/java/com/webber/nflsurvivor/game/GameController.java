package com.webber.nflsurvivor.game;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<Game>> getGamesForYearAndWeek(@RequestParam int year, @RequestParam int week) {
        return ResponseEntity.ok(gameService.findGamesForWeek(week));
    }

    @GetMapping("/active-week")
    public ResponseEntity<Integer> getActiveGameWeek() {
        return ResponseEntity.ok(gameService.getActiveGameWeek());
    }

}
