package com.games.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author lzn
 * @Description Unit test of RoundRobinTurn strategy
 */
@ExtendWith(MockitoExtension.class)
public class RoundRobinTurnTest {

    @InjectMocks
    RoundRobinTurn roundRobinTurn;

    int seed = 5;

    @Test
    void getNextTest() {
        int prev = roundRobinTurn.getNext(seed);
        for (int i = 0; i < 20; i++) {
            Assertions.assertEquals(i % seed, prev);
            prev = roundRobinTurn.getNext(seed);
        }
    }
}
