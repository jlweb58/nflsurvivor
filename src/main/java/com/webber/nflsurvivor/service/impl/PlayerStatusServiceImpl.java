package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.domain.WeeklyGameSelection;
import com.webber.nflsurvivor.game.Game;
import com.webber.nflsurvivor.game.GameResult;
import com.webber.nflsurvivor.game.GameService;
import com.webber.nflsurvivor.service.PlayerStatusService;
import com.webber.nflsurvivor.service.WeeklyGameSelectionService;
import com.webber.nflsurvivor.user.PlayerStatus;
import com.webber.nflsurvivor.user.User;
import com.webber.nflsurvivor.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerStatusServiceImpl implements PlayerStatusService {

    private final UserService userService;

    private final GameService gameService;

    private final WeeklyGameSelectionService weeklyGameSelectionService;

    @Autowired
    public PlayerStatusServiceImpl(UserService userService, GameService gameService, WeeklyGameSelectionService weeklyGameSelectionService) {
        this.userService = userService;
        this.gameService = gameService;
        this.weeklyGameSelectionService = weeklyGameSelectionService;
    }


    @Override
    public void updateGlobalPlayerStatus() {
        List<User> allUsers = userService.findAllUsers();
        for (User user : allUsers) {
            if (user.isEliminated()) {
                continue;
            }
           List<WeeklyGameSelection> playerSelections = weeklyGameSelectionService.findForUser(user);
            int weeksLost = 0;
            for (WeeklyGameSelection gameSelection : playerSelections) {
                if(gameSelection.getGameResult().equals(GameResult.LOSS)) {
                    weeksLost++;
                    if (weeksLost == 2) {
                        user.setPlayerStatus(PlayerStatus.ELIMINATED);
                        userService.save(user);
                        break;
                    }
                }
            }
        }

    }
}
