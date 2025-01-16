package com.webber.nflsurvivor.controller;

import com.webber.nflsurvivor.game.Game;
import com.webber.nflsurvivor.service.ScheduleImportService;
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

import java.util.Set;

@RestController
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/schedule")
public class ScheduleImportController {

    private final ScheduleImportService scheduleImportService;
    private final static Logger LOG = LoggerFactory.getLogger(ScheduleImportController.class);

    @Autowired
    public ScheduleImportController(ScheduleImportService scheduleImportService) {
        this.scheduleImportService = scheduleImportService;
    }


    @GetMapping("/import")
    public Set<Game> importGamesForWeekAndYear(@RequestParam Integer week, @RequestParam Integer year ) {
        return scheduleImportService.importScheduleForWeekAndYear(week, year);
    }

    @GetMapping("/import-weeks")
    public ResponseEntity<String> importScheduleWeeksForYear( @RequestParam Integer year) {
        scheduleImportService.importScheduleWeeksForYear(year);
        return ResponseEntity.ok("OK");
    }

}
