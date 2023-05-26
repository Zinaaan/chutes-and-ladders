package com.games.service;

import com.games.entity.Player;
import com.games.entity.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * @author lzn
 * @Description Publish game global notifications
 */
@Service
public class GameEventPublisher implements ApplicationEventPublisherAware {
    static Logger logger = LoggerFactory.getLogger(GameEventPublisher.class);
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void register(Player player, int step) {
        logger.info("Player register: {}", player.getName());
        publisher.publishEvent(new GameEvent(this, step));
    }
}
