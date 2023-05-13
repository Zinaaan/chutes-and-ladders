package com.priceline.chutes.service;

import com.priceline.chutes.enums.TurnType;
import org.springframework.stereotype.Service;

/**
 * @author lzn
 * @Description
 */
@Service
public class TurnService {

    public TurnStrategy findTurnStrategy() {
        return TurnStrategyFactory.getTurnStrategy(TurnType.ROUND_ROBIN);
    }
}
