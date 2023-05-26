package com.games.exceptions;

/**
 * @author lzn
 * @Description Throw exception if board size is invalid
 */
public class InvalidGamePlayerException extends RuntimeException {

    public InvalidGamePlayerException(String message) {
        super(message);
    }
}
