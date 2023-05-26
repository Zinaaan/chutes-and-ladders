package com.games.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author lzn
 * @Description Unit test of validating ChuteAndLadderGame if method given null
 */
@ExtendWith(MockitoExtension.class)
public class ValidatingChuteAndLadderGameImplMethodGivenNullTest {

    @Mock
    private ChutesAndLadderGameImpl game;

    @Test
    void givenNull_whenValidate_thenConstraintViolationException() {
        Mockito.doThrow(ConstraintViolationException.class).when(game).getPlayerWithTurn(null);
        assertThrows(ConstraintViolationException.class, () -> game.getPlayerWithTurn(null));
        Mockito.doThrow(ConstraintViolationException.class).when(game).makeMove(null, null);
        assertThrows(ConstraintViolationException.class, () -> game.makeMove(null, null));
        Mockito.doThrow(ConstraintViolationException.class).when(game).addPlayer(null, null);
        assertThrows(ConstraintViolationException.class, () -> game.addPlayer(null, null));
        Mockito.doThrow(ConstraintViolationException.class).when(game).removePlayer(null, null);
        assertThrows(ConstraintViolationException.class, () -> game.removePlayer(null, null));
        Mockito.doThrow(ConstraintViolationException.class).when(game).getGameState(null);
        assertThrows(ConstraintViolationException.class, () -> game.getGameState(null));
        Mockito.doThrow(ConstraintViolationException.class).when(game).getWinningPlayerStats(null);
        assertThrows(ConstraintViolationException.class, () -> game.getWinningPlayerStats(null));
        Mockito.doThrow(ConstraintViolationException.class).when(game).getPlayerLocations(null, null);
        assertThrows(ConstraintViolationException.class, () -> game.getPlayerLocations(null, null));
    }
}
