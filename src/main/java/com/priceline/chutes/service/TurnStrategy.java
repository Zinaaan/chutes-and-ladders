package com.priceline.chutes.service;

import com.priceline.chutes.enums.TurnType;

/**
 * @author lzn
 * @Description Player selection strategy
 */
public interface TurnStrategy {

    /**
     * iterate to the next position in the range
     * @param size range
     * @return next position in the range
     */
    int getNext(int size);

    /**
     * Get Turn type of TurnStrategy
     * @return type of TurnStrategy
     */
    TurnType getTurnType();
}
