package com.priceline.chutes.service;

import com.priceline.chutes.enums.RollType;

/**
 * @author lzn
 * @Description Props that players use to move
 */
public interface RollingStrategy {

    /**
     * the rolling strategy need to be followed
     *
     * @return rolling result
     */
    int roll();

    /**
     * Get prop strategy type
     * @return strategy type
     */
    RollType getRollType();
}
