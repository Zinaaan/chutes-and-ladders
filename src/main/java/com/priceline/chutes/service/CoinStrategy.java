package com.priceline.chutes.service;

import com.priceline.chutes.enums.RollType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author lzn
 * @Description Coin strategy: The coin have 2 sides and each roll is any number between 1 and 2
 */
@Service
public class CoinStrategy implements RollingStrategy {
    static Logger logger = LoggerFactory.getLogger(CoinStrategy.class);

    @Override
    public int roll() {
        int result = new Random().nextInt(2) + 1;
        logger.info("Current player get " + result + " by rolling the coin");
        return result;
    }

    @Override
    public RollType getRollType() {
        return RollType.Coin;
    }
}
