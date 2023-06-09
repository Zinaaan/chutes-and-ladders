package com.games.service;

import com.games.entity.ComputerPlayer;
import com.games.entity.NormalPlayer;
import com.games.entity.Player;
import com.games.entity.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzn
 * @Description Service of game player
 */
@Service
public class PlayerService {
    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    public List<Player> findAllPlayers() {
        return List.of(new NormalPlayer("John"), new NormalPlayer("David"), new ComputerPlayer("Han"));
    }

    @EventListener
    public void notifyGameProgress(GameEvent event) {
        logger.info("Game Global Notification: Player has moved to {}", event.getStep());
        logger.info("-----------------------------");
    }
}
