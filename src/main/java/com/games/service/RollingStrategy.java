package com.games.service;

import com.games.enums.RollType;

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
