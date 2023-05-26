package com.games.builder;

import com.games.service.ChuteStrategy;
import com.games.service.LadderStrategy;
import com.games.entity.NormalPlayer;
import com.games.exceptions.InvalidBoardSizeException;
import com.games.service.DiceStrategy;
import com.games.service.RoundRobinTurn;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author lzn
 * @Description Unit test of GameServiceBuilder
 */
@ExtendWith(MockitoExtension.class)
public class GameServiceBuilderTest {

    @InjectMocks
    GameBuilder gameBuilder;
    @Mock
    RoundRobinTurn roundRobinTurn;
    @Mock
    ChuteStrategy chuteStrategy;
    @Mock
    LadderStrategy ladderStrategy;
    @Mock
    DiceStrategy diceStrategy;

    @Test
    void whenBuildGameWithLowBoard_thenThrowExceptions() {
        assertThrows(InvalidBoardSizeException.class, () -> gameBuilder.buildGame(2, 2, 1,
                1, List.of(new NormalPlayer("Lucy"), new NormalPlayer("Lily")), diceStrategy, List.of(chuteStrategy, ladderStrategy),
                roundRobinTurn));
    }
}
