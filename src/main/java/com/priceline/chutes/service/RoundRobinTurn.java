package com.priceline.chutes.service;

import com.priceline.chutes.enums.TurnType;
import org.springframework.stereotype.Service;

/**
 * @author lzn
 * @Description Simple implement of Round-robin(cyclic executive), assigned to each player in circular order
 */
@Service
public class RoundRobinTurn implements TurnStrategy {

    private int turn;

    public RoundRobinTurn() {
        this.turn = -1;
    }

    @Override
    public int getNext(int size) {
        turn = (turn + 1) % size;
        return turn;
    }

    @Override
    public TurnType getTurnType() {
        return TurnType.ROUND_ROBIN;
    }
}
