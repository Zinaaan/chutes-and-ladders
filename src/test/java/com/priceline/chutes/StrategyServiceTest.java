package com.priceline.chutes;

import com.priceline.chutes.enums.RollType;
import com.priceline.chutes.enums.RuleType;
import com.priceline.chutes.enums.TurnType;
import com.priceline.chutes.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lzn
 * @Description Integration test of all strategies
 */
@SpringBootTest
public class StrategyServiceTest {

    @Autowired
    RollingService rollingService;
    @Autowired
    RuleService ruleService;
    @Autowired
    TurnService turnService;

    @Test
    void whenInvokingServiceMethod_thenSuccess() {
        Assertions.assertSame(rollingService.findRollingStrategy(), RollingStrategyFactory.getRollingStrategy(RollType.Dice));
        Assertions.assertArrayEquals(ruleService.findAllRules().toArray(),
                new Object[]{RuleStrategyFactory.getGameRuleStrategy(RuleType.CHUTE), RuleStrategyFactory.getGameRuleStrategy(RuleType.LADDER)});
        Assertions.assertSame(turnService.findTurnStrategy(), TurnStrategyFactory.getTurnStrategy(TurnType.ROUND_ROBIN));
    }
}
