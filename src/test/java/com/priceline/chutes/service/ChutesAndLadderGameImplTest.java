package com.priceline.chutes.service;

import com.priceline.chutes.builder.GameGenerator;
import com.priceline.chutes.builder.GameInitializer;
import com.priceline.chutes.entity.ComputerPlayer;
import com.priceline.chutes.entity.GameEvent;
import com.priceline.chutes.entity.NormalPlayer;
import com.priceline.chutes.entity.Player;
import com.priceline.chutes.enums.GameState;
import com.priceline.chutes.exceptions.InvalidGamePlayerException;
import com.priceline.chutes.exceptions.InvalidGameStateForActionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzn
 * @Description Unit test of ChutesAndLadderGameImpl
 */
@ExtendWith(MockitoExtension.class)
class ChutesAndLadderGameImplTest {

    @Mock
    private GameInitializer gameInitializer;
    @Mock
    private ChutesAndLadderGameImpl game;
    @Mock
    private ChuteStrategy chuteStrategy;
    @Mock
    private LadderStrategy ladderStrategy;
    @Mock
    private DiceStrategy rollingStrategy;
    @Mock
    private RoundRobinTurn roundRobinTurn;
    private final int width = 10, height = 10, chute = 5, ladder = 7;
    private GameGenerator generator;
    private List<Player> playersList;

    @BeforeEach
    void setup() {
        playersList = List.of(new NormalPlayer("John"), new NormalPlayer("David"), new ComputerPlayer("Han"));
        generator = new GameGenerator.Builder(width, height, chute, ladder).
                withPlayers(playersList).withRuleStrategies(List.of(chuteStrategy, ladderStrategy)).withTurnStrategy(roundRobinTurn)
                .withRollingStrategy(rollingStrategy).build();
    }

    @Test
    void initializeTest() {
        Mockito.when(gameInitializer.generateGameWithStrategy()).thenReturn(generator);
        Assertions.assertEquals(gameInitializer.generateGameWithStrategy(), generator);
    }

    @Test
    void getPlayerWithTurnTest() {
        Mockito.when(game.getPlayerWithTurn(generator)).thenReturn(playersList.get(0));
        Assertions.assertEquals(game.getPlayerWithTurn(generator), playersList.get(0));
    }

    @Test
    void addPlayerTest() {
        List<Player> playersList = new ArrayList<>();
        playersList.add(new NormalPlayer("John"));
        playersList.add(new NormalPlayer("David"));
        playersList.add(new ComputerPlayer("Han"));
        generator = new GameGenerator.Builder(width, height, chute, ladder).
                withPlayers(playersList).withRuleStrategies(List.of(chuteStrategy, ladderStrategy)).withTurnStrategy(roundRobinTurn)
                .withRollingStrategy(rollingStrategy).build();
        Player player = new NormalPlayer("Bob");
        playersList.add(player);
        game.addPlayer(player, generator);
        List<Player> geList = generator.getPlayers();
        Assertions.assertArrayEquals(playersList.toArray(), geList.toArray());
        Player John = new NormalPlayer("John");
        Mockito.doThrow(InvalidGamePlayerException.class).when(game).addPlayer(John, generator);
        Assertions.assertThrows(InvalidGamePlayerException.class, () -> game.addPlayer(John, generator), "this player is already in the game");
        generator.setGameState(GameState.START);
        Mockito.doThrow(InvalidGameStateForActionException.class).when(game).addPlayer(John, generator);
        Assertions.assertThrows(InvalidGameStateForActionException.class, () -> game.addPlayer(John, generator));
        generator.setGameState(GameState.END);
        Mockito.doThrow(InvalidGameStateForActionException.class).when(game).addPlayer(John, generator);
        Assertions.assertThrows(InvalidGameStateForActionException.class, () -> game.addPlayer(John, generator));
    }

    @Test
    void removePlayerTest() {
        List<Player> playersList = new ArrayList<>();
        Player John = new NormalPlayer("John");
        playersList.add(John);
        playersList.add(new NormalPlayer("David"));
        playersList.add(new ComputerPlayer("Han"));
        generator = new GameGenerator.Builder(width, height, chute, ladder).
                withPlayers(playersList).withRuleStrategies(List.of(chuteStrategy, ladderStrategy)).withTurnStrategy(roundRobinTurn)
                .withRollingStrategy(rollingStrategy).build();
        playersList.remove(John);
        game.removePlayer(John, generator);
        List<Player> geList = generator.getPlayers();
        Assertions.assertArrayEquals(playersList.toArray(), geList.toArray());
        Player player = new NormalPlayer("Bob");
        Mockito.doThrow(InvalidGamePlayerException.class).when(game).removePlayer(player, generator);
        Assertions.assertThrows(InvalidGamePlayerException.class, () -> game.removePlayer(player, generator));
        generator.setGameState(GameState.START);
        Mockito.doThrow(InvalidGameStateForActionException.class).when(game).removePlayer(John, generator);
        Assertions.assertThrows(InvalidGameStateForActionException.class, () -> game.removePlayer(John, generator));
        generator.setGameState(GameState.END);
        Mockito.doThrow(InvalidGameStateForActionException.class).when(game).removePlayer(John, generator);
        Assertions.assertThrows(InvalidGameStateForActionException.class, () -> game.removePlayer(John, generator));
    }

    @Test
    void getGameStateTest() {
        Mockito.when(game.getGameState(generator)).thenReturn(GameState.WAITING);
        Assertions.assertSame(game.getGameState(generator), GameState.WAITING);
        generator.setGameState(GameState.START);
        Mockito.when(game.getGameState(generator)).thenReturn(GameState.START);
        Assertions.assertSame(game.getGameState(generator), GameState.START);
    }

    @Test
    void getWinningPlayerStatsTest() {
        generator.setGameState(GameState.END);
        Mockito.when(game.getWinningPlayerStats(generator)).thenReturn(playersList.get(0));
        Assertions.assertSame(game.getWinningPlayerStats(generator), playersList.get(0));
        generator.setGameState(GameState.START);
        Mockito.doThrow(InvalidGameStateForActionException.class).when(game).getWinningPlayerStats(generator);
        Assertions.assertThrows(InvalidGameStateForActionException.class, () -> game.getWinningPlayerStats(generator));
    }

    @Test
    void getPlayerLocationsTest() {
        generator.setGameState(GameState.START);
        Mockito.when(game.getPlayerLocations(playersList.get(0), generator)).
                thenReturn(new GameEvent(this, generator.getPlayerLocations().get(playersList.get(0))));
        Assertions.assertSame(game.getPlayerLocations(playersList.get(0), generator).getStep(),
                new GameEvent(this, generator.getPlayerLocations().get(playersList.get(0))).getStep());
        Player bob = new NormalPlayer("Bob");
        Mockito.when(game.getPlayerLocations(bob, generator)).thenThrow(InvalidGamePlayerException.class);
        Assertions.assertThrows(InvalidGamePlayerException.class, () -> game.getPlayerLocations(bob, generator));
        generator.setGameState(GameState.END);
        Mockito.when(game.getPlayerLocations(playersList.get(0), generator)).thenThrow(InvalidGameStateForActionException.class);
        Assertions.assertThrows(InvalidGameStateForActionException.class, () -> game.getPlayerLocations(playersList.get(0), generator));
    }
}