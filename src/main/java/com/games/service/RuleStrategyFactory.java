package com.games.service;

import com.games.enums.RuleType;
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
 * @Description Rule Strategy factory, integrated with spring
 */
@Component
public class RuleStrategyFactory implements ApplicationContextAware {
    private static final Map<RuleType, RuleStrategy> STRATEGIES = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(RuleStrategyFactory.class);

    public static RuleStrategy getGameRuleStrategy(RuleType ruleType) {
        return Optional.ofNullable(STRATEGIES.get(ruleType)).orElseThrow(() -> new IllegalArgumentException("ruleType should not be empty."));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RuleStrategy> map = applicationContext.getBeansOfType(RuleStrategy.class);
        map.forEach((key, value) -> STRATEGIES.put(value.getRuleType(), value));
        logger.info("RuleStrategy-----------------" + STRATEGIES);
    }
}
