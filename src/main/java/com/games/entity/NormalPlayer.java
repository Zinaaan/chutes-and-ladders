package com.games.entity;

/**
 * @author lzn
 * @Description Real players
 */
public class NormalPlayer extends Player {

    public NormalPlayer(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Hi, I'm a normal player names " + name;
    }
}
