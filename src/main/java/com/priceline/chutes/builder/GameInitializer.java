package com.priceline.chutes.builder;

import com.priceline.chutes.annotation.GameListener;
import com.priceline.chutes.service.PlayerService;
import com.priceline.chutes.service.RollingService;
import com.priceline.chutes.service.RuleService;
import com.priceline.chutes.service.TurnService;
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
