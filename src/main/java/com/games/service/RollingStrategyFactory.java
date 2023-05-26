package com.games.service;

import com.games.enums.RollType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author lzn
 * @Description Rolling strategy factory, integrated with spring
 */
@Component
public class RollingStrategyFactory implements ApplicationContextAware {
    private final static Map<RollType, RollingStrategy> STRATEGIES = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(RollingStrategyFactory.class);

    public static RollingStrategy getRollingStrategy(RollType rollType) {
        return Optional.ofNullable(STRATEGIES.get(rollType)).orElseThrow(() -> new IllegalArgumentException("rollType should not be empty."));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RollingStrategy> map = applicationContext.getBeansOfType(RollingStrategy.class);
        map.forEach((key, value) -> STRATEGIES.put(value.getRollType(), value));
        logger.info("RollingStrategy-----------------" + STRATEGIES);
    }
}