package com.games.controller;

import com.games.entity.Player;
import com.games.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzn
 * @Description Game controller, process the request submitted by the user accordingly and return the result
 */
@RestController
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * start the game
     */
    @GetMapping(value = "/playGame")
    public String playGame() {
        Player player = gameService.playGame();
        logger.info(player.getName() + " has won the Game!!");
        return player.getName() + " has won the Game!!";
    }

}
