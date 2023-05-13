package com.priceline.chutes.builder;

import com.priceline.chutes.entity.Player;
import com.priceline.chutes.service.RollingStrategy;
import com.priceline.chutes.service.RuleStrategy;
import com.priceline.chutes.service.TurnStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lzn
 * @Description Build game by invoke builder of GameService generator
 */
@Component
public class GameBuilder {

    public GameGenerator buildGame(int boardWidth, int boardHeight, int chutes, int ladders, List<Player> players, RollingStrategy rollingStrategy,
                                   List<RuleStrategy> rules, TurnStrategy turnStrategy) {
        return new GameGenerator.Builder(boardWidth, boardHeight, chutes, ladders).
                withPlayers(players).withRuleStrategies(rules).withTurnStrategy(turnStrategy).withRollingStrategy(rollingStrategy).build();
    }
}
