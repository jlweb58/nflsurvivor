package com.webber.nflsurvivor.cron;

import com.webber.nflsurvivor.service.PlayerStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UpdatePlayerStatusTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePlayerStatusTask.class);

    private static final long HOUR = 1000  * 60 * 60;

    private final PlayerStatusService playerStatusService;

    @Autowired
    public UpdatePlayerStatusTask(PlayerStatusService playerStatusService) {
        this.playerStatusService = playerStatusService;
    }

    @Scheduled(fixedRate = HOUR)
    public void updateGlobalPlayerStatus() {
        LOGGER.info("Updating global player status: " + LocalDateTime.now());
        playerStatusService.updateGlobalPlayerStatus();
    }
}
