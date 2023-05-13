package com.priceline.chutes.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

/**
 * @author lzn
 * @Description Unit test of LadderStrategy
 */
@ExtendWith(MockitoExtension.class)
public class LadderStrategyTest {
    @InjectMocks
    LadderStrategy ladderStrategy;

    private final int[] board = new int[100];

    @RepeatedTest(5)
    void generateGameRuleTest() {
        Map<Integer, Integer> realMap = ladderStrategy.generateGameRule(board, 7);
        for (Map.Entry<Integer, Integer> entry : realMap.entrySet()) {
            Assertions.assertTrue(entry.getKey() < entry.getValue());
            Assertions.assertTrue(entry.getKey() != 0);
            Assertions.assertTrue(entry.getKey() != 100);
        }
    }
}
