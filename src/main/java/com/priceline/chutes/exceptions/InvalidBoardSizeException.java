package com.priceline.chutes.exceptions;

/**
 * @author lzn
 * @Description Throw exception if board size is invalid
 */
public class InvalidBoardSizeException extends RuntimeException {

    public InvalidBoardSizeException(String message) {
        super(message);
    }
}
