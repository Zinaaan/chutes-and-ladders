package com.games.service;

import com.games.annotation.GameListener;
import com.games.builder.GameGenerator;
import com.games.builder.GameInitializer;
import com.games.entity.GameEvent;
import com.games.entity.Player;
import com.games.enums.GameState;
import com.games.exceptions.InvalidGamePlayerException;
import com.games.exceptions.InvalidGameStateForActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lzn
 * @Description Chute and Ladder game
 */
@Service
public class ChutesAndLadderGameImpl implements GameService {
    static Logger logger = LoggerFactory.getLogger(ChutesAndLadderGameImpl.class);
    private final GameInitializer gameInitializer;
    private final PlayerService playerService;

    public ChutesAndLadderGameImpl(GameInitializer gameInitializer, PlayerService playerService) {
        this.gameInitializer = gameInitializer;
        this.playerService = playerService;
    }

    @Override
    public GameGenerator initialize() {
        GameGenerator generator = gameInitializer.generateGameWithStrategy();
        initializeGameRules(generator);
        logger.info("GameService is initialized!");
        return generator;
    }

    @Override
    public Player playGame() {
        GameGenerator generator = initialize();
        logger.info("Now let's start the ChuteAndLadder GameService!");
        logger.info("-----------------------------");
        while (!isOver(generator)) {
            Player player = getPlayerWithTurn(generator);
            //When each player starts a round of game, they need to use props to get the number of steps to move, and then move the corresponding distance
            makeMove(generator, player.generateMove(generator.getRollingStrategy()));
            //get the current players position
            if (!isOver(generator)) {
                playerService.notifyGameProgress(getPlayerLocations(player, generator));
            }
        }
        return getWinningPlayerStats(generator);
    }

    @Override
    public void forceToStop(GameGenerator generator) {
        generator.setGameState(GameState.END);
        logger.info("GameService has been forcibly stopped!");
    }

    /**
     * initialize the game rules
     */
    @Validated
    public void initializeGameRules(@NotNull GameGenerator generator) {
        List<RuleStrategy> rules = generator.getRules();
        Map<Integer, Integer> chuteLocations = null, ladderLocations = null;
        for (RuleStrategy ruleStrategy : rules) {
            if (ruleStrategy instanceof ChuteStrategy) {
                chuteLocations = ruleStrategy.generateGameRule(generator.getBoard(), generator.getChuteCount());
            } else if (ruleStrategy instanceof LadderStrategy) {
                ladderLocations = ruleStrategy.generateGameRule(generator.getBoard(), generator.getLaddersCount());
            }
        }

        logger.info("chuteLocations: " + chuteLocations);
        logger.info("ladderLocations: " + ladderLocations);
        generator.setChuteLocations(chuteLocations);
        generator.setLadderLocations(ladderLocations);
    }

    @Override
    public Player getPlayerWithTurn(GameGenerator gameGenerator) {
        return gameGenerator.getPlayers().get(gameGenerator.getPlayerWithTurn());
    }

    @Override
    public void makeMove(GameGenerator generator, GameEvent gameEvent) {
        Player player = getPlayerWithTurn(generator);
        Map<Player, Integer> playerLocations = generator.getPlayerLocations();
        Map<Integer, Integer> chuteLocations = generator.getChuteLocations();
        Map<Integer, Integer> ladderLocations = generator.getLadderLocations();
        int prevStep = playerLocations.getOrDefault(player, 0);
        int currentStep = prevStep + gameEvent.getStep();
        int boardSize = generator.getBoard().length;
        //if players gameEvent to the start of chute or start of ladder, they will slide to the corresponding position
        if (chuteLocations.containsKey(currentStep)) {
            playerLocations.put(player, chuteLocations.get(currentStep));
            logger.info("I'm a unlucky one names " + player.getName() + " and I'll took a chute from " + currentStep + " to " + chuteLocations.get(currentStep));
        } else if (ladderLocations.containsKey(currentStep)) {
            playerLocations.put(player, ladderLocations.get(currentStep));
            logger.info("I'm a lucky person names " + player.getName() + " and I'll took a ladder from " + currentStep + " to " + ladderLocations.get(currentStep));
        } else {
            //if not, gameEvent to the current position
            playerLocations.put(player, currentStep);
            logger.info("I'm " + player.getName() + " and I moved from " + prevStep + " to " + currentStep);
        }
        //check if the current player's position has reached the end
        if (playerLocations.get(player) == boardSize) {
            // the current player has won the game!
            generator.setGameState(GameState.END);
        } else {
            if (playerLocations.get(player) > boardSize) {
                logger.info("Oh! the number is " + playerLocations.get(player) + " bigger than " + boardSize + ", I can't gameEvent and I need to stay in place ");
                playerLocations.put(player, prevStep);
            }
            // make sure that the turn is advanced
            generator.setPlayerWithTurn(generator.getTurnStrategy().getNext(generator.getPlayers().size()));
        }
    }

    @Override
    @GameListener
    public GameGenerator addPlayer(Player player, GameGenerator gameGenerator) {
        if (gameGenerator.getPlayers().contains(player)) {
            throw new InvalidGamePlayerException("this player is already in the game");
        }
        if (gameGenerator.getGameState().equals(GameState.START)) {
            throw new InvalidGameStateForActionException("can't add player mid game");
        }
        if (isOver(gameGenerator)) {
            throw new InvalidGameStateForActionException("game has already ended");
        }
        List<Player> players = new ArrayList<>(gameGenerator.getPlayers());
        players.add(player);
        gameGenerator.setPlayers(players);
        gameGenerator.getPlayerLocations().put(player, 0);
        logger.info("Adding new player, " + player.toString());
        logger.info("Now total player is: " + players.toString());
        return gameGenerator;
    }

    @Override
    @GameListener
    public GameGenerator removePlayer(Player player, GameGenerator gameGenerator) {
        if (!gameGenerator.getPlayers().contains(player)) {
            throw new InvalidGamePlayerException("this player was not registered for this game");
        }
        if (gameGenerator.getGameState().equals(GameState.START)) {
            throw new InvalidGameStateForActionException("can't remove player mid game");
        }
        if (isOver(gameGenerator)) {
            throw new InvalidGameStateForActionException("game has already ended");
        }
        List<Player> players = new ArrayList<>(gameGenerator.getPlayers());
        players.remove(player);
        gameGenerator.setPlayers(players);
        gameGenerator.getPlayerLocations().remove(player);
        logger.info("Player " + player.getName() + " has been removed");
        logger.info("Now total player is: " + players.toString());
        return gameGenerator;
    }

    @Override
    public GameState getGameState(GameGenerator gameGenerator) {
        return gameGenerator.getGameState();
    }

    @Override
    public Player getWinningPlayerStats(GameGenerator gameGenerator) {
        if (isOver(gameGenerator)) {
            return getPlayerWithTurn(gameGenerator);
        } else {
            throw new InvalidGameStateForActionException("GameService in progress: winner hasn't been decided yet");
        }
    }

    @Override
    public GameEvent getPlayerLocations(Player player, GameGenerator gameGenerator) {
        if (!gameGenerator.getPlayers().contains(player)) {
            throw new InvalidGamePlayerException("this player was not registered for this game");
        }
        if (isOver(gameGenerator)) {
            throw new InvalidGameStateForActionException("can't get locations from the ended game");
        }
        return new GameEvent(this, gameGenerator.getPlayerLocations().get(player));
    }
}
