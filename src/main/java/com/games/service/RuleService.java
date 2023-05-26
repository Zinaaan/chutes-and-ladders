package com.games.service;

import com.games.enums.RuleType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzn
 * @Description Service of game rules
 */
@Service
public class RuleService {

    public List<RuleStrategy> findAllRules() {
        return List.of(RuleStrategyFactory.getGameRuleStrategy(RuleType.CHUTE), RuleStrategyFactory.getGameRuleStrategy(RuleType.LADDER));
    }
}
