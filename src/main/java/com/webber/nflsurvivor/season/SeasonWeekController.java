package com.webber.nflsurvivor.season;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/season-weeks")
public class SeasonWeekController {

    private final SeasonWeekService seasonWeekService;

    public SeasonWeekController(SeasonWeekService seasonWeekService) {
        this.seasonWeekService = seasonWeekService;
    }

    @GetMapping("/active-week")
    public ResponseEntity<Integer> getActiveGameWeek() {
        return ResponseEntity.ok(seasonWeekService.getActiveGameWeek());
    }

}
