package com.priceline.chutes.exceptions;

/**
 * @author lzn
 * @Description throw exception if game state inconsistent with expectations
 */
public class InvalidGameStateForActionException extends RuntimeException {

    public InvalidGameStateForActionException(String message) {
        super(message);
    }
}
