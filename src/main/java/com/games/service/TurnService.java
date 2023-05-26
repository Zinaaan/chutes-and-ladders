package com.games.service;

import com.games.enums.TurnType;
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
