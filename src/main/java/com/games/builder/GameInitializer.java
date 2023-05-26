package com.games.builder;

import com.games.annotation.GameListener;
import com.games.service.PlayerService;
import com.games.service.RollingService;
import com.games.service.RuleService;
import com.games.service.TurnService;
import org.springframework.stereotype.Service;

/**
 * @author lzn
 * @Description Initialize the game and generate game with essential parameters
 */
@Service
public class GameInitializer {

    private final GameBuilder gameBuilder;
    private final PlayerService playerService;
    private final RuleService ruleService;
    private final RollingService rollingService;
    private final TurnService turnService;


    public GameInitializer(GameBuilder gameBuilder, PlayerService playerService, RuleService ruleService, RollingService rollingService, TurnService turnService) {
        this.gameBuilder = gameBuilder;
        this.playerService = playerService;
        this.ruleService = ruleService;
        this.rollingService = rollingService;
        this.turnService = turnService;
    }

    @GameListener
    public GameGenerator generateGameWithStrategy() {
        return gameBuilder.buildGame(10, 10, 5, 7, playerService.findAllPlayers(), rollingService.findRollingStrategy(),
                ruleService.findAllRules(), turnService.findTurnStrategy());
    }
}
