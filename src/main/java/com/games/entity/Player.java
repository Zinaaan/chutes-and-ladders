package com.games.entity;

import com.games.service.RollingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author lzn
 * @Description Parent class for all players
 */
public class Player {
    static Logger logger = LoggerFactory.getLogger(Player.class);
    protected final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public GameEvent generateMove(RollingStrategy strategy) {
        int roll = strategy.roll();
        logger.info(this.toString() + " and I rolled " + roll);
        return new GameEvent(this, roll);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
