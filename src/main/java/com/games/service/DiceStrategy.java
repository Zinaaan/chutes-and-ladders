package com.games.service;

import com.games.enums.RollType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author lzn
 * @Description Dice strategy: The dice have 6 sides and each roll is any number between 1 and 6
 */
@Service
public class DiceStrategy implements RollingStrategy {
    static Logger logger = LoggerFactory.getLogger(DiceStrategy.class);

    @Override
    public int roll() {
        int result = new Random().nextInt(6) + 1;
        logger.info("Current player get " + result + " by rolling the dice");
        return result;
    }

    @Override
    public RollType getRollType() {
        return RollType.Dice;
    }
}
