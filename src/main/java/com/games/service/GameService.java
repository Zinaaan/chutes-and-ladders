package com.games.service;

import com.games.entity.Player;
import com.games.builder.GameGenerator;
import com.games.entity.GameEvent;
import com.games.enums.GameState;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author lzn
 * @Description Service Center for the entire game
 */
@Validated
public interface GameService {

    /**
     * initialize the game
     * @return early game generator
     */
    GameGenerator initialize();

    /**
     * start the game
     *
     * @return the winner
     */
    Player playGame();

    /**
     * Determine if the game is over
     *
     * @param generator game generator
     * @return true if game is end, otherwise return false
     */
    default boolean isOver(@NotNull GameGenerator generator) {
        return this.getGameState(generator).equals(GameState.END);
    }

    /**
     * Forced stop of the game in progress
     *
     * @param generator game generator
     */
    void forceToStop(@NotNull GameGenerator generator);

    /**
     * get the current state of the game
     *
     * @param generator game generator
     * @return GameState
     */
    GameState getGameState(@NotNull GameGenerator generator);

    /**
     * get the current player
     *
     * @param generator game generator
     * @return current player
     */
    Player getPlayerWithTurn(@NotNull GameGenerator generator);

    /**
     * check if the game is ended and return the winner
     *
     * @param generator game generator
     * @return the winner
     */
    Player getWinningPlayerStats(@NotNull GameGenerator generator);

    /**
     * get locations of current player
     *
     * @param player the player to be added
     * @param generator game generator
     * @return location of current player
     */
    GameEvent getPlayerLocations(@NotNull Player player, @NotNull GameGenerator generator);

    /**
     * GameEvent the corresponding distance according to the number
     *
     * @param generator game generator
     * @param gameEvent how many steps should be moved
     */
    void makeMove(@NotNull GameGenerator generator, @NotNull GameEvent gameEvent);

    /**
     * add a player to the current game
     *
     * @param player the player to be added
     * @param generator game generator
     * @return generator current game generator
     */
    GameGenerator addPlayer(@NotNull Player player, @NotNull GameGenerator generator);

    /**
     * remove a player to the current game
     *
     * @param player the player to be removed
     * @param generator game generator
     * @return generator current game generator
     */
    GameGenerator removePlayer(@NotNull Player player, @NotNull GameGenerator generator);
}
