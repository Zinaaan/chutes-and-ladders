package com.priceline.chutes.service;

import com.priceline.chutes.enums.RollType;
import org.springframework.stereotype.Service;

/**
 * @author lzn
 * @Description
 */
@Service
public class RollingService {

    public RollingStrategy findRollingStrategy() {
        return RollingStrategyFactory.getRollingStrategy(RollType.Dice);
    }
}
