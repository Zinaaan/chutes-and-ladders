package com.priceline.chutes.service;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author lzn
 * @Description Unit test of oinStrategy
 */
@ExtendWith(MockitoExtension.class)
public class CoinStrategyTest {

    @InjectMocks
    CoinStrategy coinStrategy;

    @RepeatedTest(5)
    void whenRolling_thenGetOneOrTwo() {
        int roll = coinStrategy.roll();
        assertTrue(roll > 0 && roll < 3);
    }
}
