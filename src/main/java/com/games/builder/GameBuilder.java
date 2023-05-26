package com.games.builder;

import com.games.entity.Player;
import com.games.service.RollingStrategy;
import com.games.service.RuleStrategy;
import com.games.service.TurnStrategy;
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
