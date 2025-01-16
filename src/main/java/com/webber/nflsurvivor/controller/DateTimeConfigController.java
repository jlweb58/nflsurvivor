package com.webber.nflsurvivor.controller;


import com.webber.nflsurvivor.configuration.DateTimeProperties;
import com.webber.nflsurvivor.util.DateTimeService;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.function.Supplier;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("config/datetime")
public class DateTimeConfigController {

    private final DateTimeProperties properties;

    private final ApplicationContext applicationContext;

    private final DateTimeService dateTimeService;

    public DateTimeConfigController(DateTimeProperties properties, ApplicationContext applicationContext, DateTimeService dateTimeService) {
        this.properties = properties;
        this.applicationContext = applicationContext;
        this.dateTimeService = dateTimeService;
    }

    @PostMapping
    public ResponseEntity<String> updateDateTime(@RequestParam boolean fixed, @RequestParam(required = false) String fixedTime) {
        try {
            // Validate the fixedTime if provided
            if (fixed && fixedTime != null && !fixedTime.isEmpty()) {
                Instant.parse(fixedTime); // Will throw if invalid
            }

            properties.setFixed(fixed);
            properties.setFixedTime(fixedTime);

            // Trigger a refresh of the scope
            ((ConfigurableApplicationContext) applicationContext)
                    .getBeanFactory()
                    .getBean(RefreshScope.class)
                    .refresh(Supplier.class);

            return ResponseEntity.ok("DateTime configuration updated successfully");
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest()
                    .body("Invalid datetime format. Use ISO-8601 format");
        }
    }

    @GetMapping
    public ResponseEntity<Instant> getCurrentDateTime() {
        return ResponseEntity.ok(dateTimeService.getCurrentDateTime());
    }
}
