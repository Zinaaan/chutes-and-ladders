package com.games.entity;

import org.springframework.context.ApplicationEvent;

/**
 * @author lzn
 * @Description Movements that players use to perform
 */
public class GameEvent extends ApplicationEvent {

    private final int step;

    public GameEvent(Object source, int step) {
        super(source);
        this.step = step;
    }

    public int getStep() {
        return step;
    }
}
