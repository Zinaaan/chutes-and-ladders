package com.games;

import com.games.controller.GameController;
import com.games.entity.NormalPlayer;
import com.games.entity.Player;
import com.games.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author lzn
 * @Description Integration test of Chutes and Ladder game
 */
@SpringBootTest
public class GameControllerTest {

    MockMvc mockMvc;
    @Autowired
    GameController gameController;
    @MockBean
    GameService gameService;
    private Player player;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.gameController).build();
        player = new NormalPlayer("John");
    }

    @Test
    void whenPlayGame_thenGameStartedAndGetWinner() throws Exception {
        when(gameService.playGame()).thenReturn(player);
        mockMvc.perform(get("/playGame")).andExpect(status().isOk());
    }
}