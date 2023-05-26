package com.games.service;

import com.games.enums.RuleType;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author lzn
 * @Description GameService strategy
 */
@Validated
public interface RuleStrategy {

    /**
     * Generate different game rules with filter
     *
     * @param board the game board
     * @param count     number of current rules
     * @return Hash table with start and end positions
     */
    Map<Integer, Integer> generateGameRule(@NotNull int[] board, @NotNull int count);

    /**
     * Get Rule type of ruleStrategy
     * @return rule type
     */
    RuleType getRuleType();
}
