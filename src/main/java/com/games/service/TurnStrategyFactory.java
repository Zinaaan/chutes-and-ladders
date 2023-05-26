package com.games.service;

import com.games.enums.TurnType;
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
 * @Description Turn strategy factory, integrated with spring
 */
@Component
public class TurnStrategyFactory implements ApplicationContextAware {

    private static final Map<TurnType, TurnStrategy> STRATEGIES = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(RuleStrategyFactory.class);

    public static TurnStrategy getTurnStrategy(TurnType turnType) {
        return Optional.ofNullable(STRATEGIES.get(turnType)).orElseThrow(() -> new IllegalArgumentException("turnType should not be empty."));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, TurnStrategy> map = applicationContext.getBeansOfType(TurnStrategy.class);
        map.forEach((key, value) -> STRATEGIES.put(value.getTurnType(), value));
        logger.info("TurnStrategy-----------------" + STRATEGIES);
    }
}
