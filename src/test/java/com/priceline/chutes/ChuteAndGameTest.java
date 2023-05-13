package com.priceline.chutes;

import com.priceline.chutes.builder.GameGenerator;
import com.priceline.chutes.entity.GameEvent;
import com.priceline.chutes.entity.NormalPlayer;
import com.priceline.chutes.entity.Player;
import com.priceline.chutes.enums.GameState;
import com.priceline.chutes.exceptions.InvalidGamePlayerException;
import com.priceline.chutes.exceptions.InvalidGameStateForActionException;
import com.priceline.chutes.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * @author lzn
 * @Description Integration test of ChuteAndGame
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {GameApplication.class})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles
public class ChuteAndGameTest {

    @Autowired
    private ChutesAndLadderGameImpl game;
    @Autowired
    private ChuteStrategy chuteStrategy;
    @Autowired
    private LadderStrategy ladderStrategy;
    @Autowired
    private RoundRobinTurn roundRobinTurn;
    @Autowired
    private DiceStrategy diceStrategy;

    @Test
    void chuteAndGameProcessTest() {
        //Test initialize
        GameGenerator generator = game.initialize();
        Assertions.assertEquals(generator.getPlayers().size(), generator.getPlayerLocations().size());
        Assertions.assertEquals(generator.getChuteLocations().size(), generator.getChuteCount());
        Assertions.assertEquals(generator.getLadderLocations().size(), generator.getLaddersCount());
        Assertions.assertEquals(generator.getTurnStrategy(), roundRobinTurn);
        Assertions.assertEquals(generator.getRollingStrategy(), diceStrategy);
        Assertions.assertArrayEquals(generator.getRules().toArray(), new Object[]{chuteStrategy, ladderStrategy});
        Assertions.assertSame(game.getPlayerWithTurn(generator), generator.getPlayers().get(generator.getPlayerWithTurn()));
        //Test move
        Map<Integer, Integer> chuteLocations = generator.getChuteLocations();
        Map<Integer, Integer> ladderLocations = generator.getLadderLocations();
        for (int i = 0; i < 20; i++) {
            Player player = game.getPlayerWithTurn(generator);
            int prev = generator.getPlayerLocations().get(player);
            int step = player.generateMove(diceStrategy).getStep();
            game.makeMove(generator, new GameEvent(this, step));
            int currentStep = prev + step;
            if (chuteLocations.containsKey(currentStep)) {
                Assertions.assertSame(generator.getPlayerLocations().get(player), chuteLocations.get(currentStep));
            }
            if (ladderLocations.containsKey(currentStep)) {
                Assertions.assertSame(generator.getPlayerLocations().get(player), ladderLocations.get(currentStep));
            }
            if (currentStep > generator.getBoard().length) {
                Assertions.assertSame(generator.getPlayerLocations().get(player), prev);
            }
            if (currentStep == generator.getBoard().length) {
                Assertions.assertSame(generator.getGameState(), GameState.END);
                break;
            }
        }
        //Test get winning player status
        generator.setGameState(GameState.WAITING);
        Assertions.assertThrows(InvalidGameStateForActionException.class, () -> game.getWinningPlayerStats(generator));
        game.forceToStop(generator);
        Assertions.assertSame(generator.getPlayers().get(generator.getPlayerWithTurn()), game.getWinningPlayerStats(generator));
        //Test get player location
        Player newPlayer = new NormalPlayer("Bob");
        Assertions.assertThrows(InvalidGamePlayerException.class, () -> game.getPlayerLocations(newPlayer, generator));
        Assertions.assertThrows(InvalidGameStateForActionException.class, () -> game.getPlayerLocations(generator.getPlayers().get(generator.getPlayerWithTurn()), generator));
        Assertions.assertSame(game.getGameState(generator), GameState.END);
        //Test add player
        Assertions.assertThrows(InvalidGamePlayerException.class, () -> game.addPlayer(generator.getPlayers().get(generator.getPlayerWithTurn()), generator));
        generator.setGameState(GameState.START);
        Assertions.assertThrows(InvalidGameStateForActionException.class, () -> game.addPlayer(newPlayer, generator));
        generator.setGameState(GameState.END);
        Assertions.assertThrows(InvalidGameStateForActionException.class, () -> game.addPlayer(newPlayer, generator));
        generator.setGameState(GameState.WAITING);
        int prevPlayersNum = generator.getPlayers().size();
        game.addPlayer(newPlayer, generator);
        Assertions.assertTrue(generator.getPlayers().contains(newPlayer));
        Assertions.assertTrue(generator.getPlayerLocations().containsKey(newPlayer));
        Assertions.assertEquals(prevPlayersNum + 1, generator.getPlayers().size());
        //Test remove player
        Player mockPlayer = new NormalPlayer("Ray");
        Assertions.assertThrows(InvalidGamePlayerException.class, () -> game.removePlayer(mockPlayer, generator));
        generator.setGameState(GameState.START);
        Assertions.assertThrows(InvalidGameStateForActionException.class, () -> game.removePlayer(generator.getPlayers().get(generator.getPlayerWithTurn()), generator));
        generator.setGameState(GameState.END);
        Assertions.assertThrows(InvalidGameStateForActionException.class, () -> game.removePlayer(generator.getPlayers().get(generator.getPlayerWithTurn()), generator));
        Player currentPlayer = generator.getPlayers().get(generator.getPlayerWithTurn());
        int prevNum = generator.getPlayers().size();
        generator.setGameState(GameState.WAITING);
        game.removePlayer(currentPlayer, generator);
        Assertions.assertFalse(generator.getPlayers().contains(currentPlayer));
        Assertions.assertFalse(generator.getPlayerLocations().containsKey(currentPlayer));
        Assertions.assertEquals(prevNum - 1, generator.getPlayers().size());


        //Finally, test playGame
        game.playGame();
    }
}
