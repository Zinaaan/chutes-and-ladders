package com.games.entity;

/**
 * @author lzn
 * @Description computer players
 */
public class ComputerPlayer extends Player {

    private final String name;

    public ComputerPlayer(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Hi, I'm a computer player names " + name;
    }
}
