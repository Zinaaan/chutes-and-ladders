package com.priceline.chutes.service;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author lzn
 * @Description Unit test of oinStrategy
 */
@ExtendWith(MockitoExtension.class)
public class DiceStrategyTest {

    @InjectMocks
    DiceStrategy diceStrategy;

    @RepeatedTest(5)
    void whenRolling_thenGetOneOrTwo() {
        int roll = diceStrategy.roll();
        assertTrue(roll > 0 && roll < 7);
    }
}
