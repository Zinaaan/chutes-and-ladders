package com.games.aop;

import com.games.builder.GameGenerator;
import com.games.entity.Player;
import com.games.service.GameEventPublisher;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lzn
 * @Description AOP that listens to method or class annotation with GameListener to dynamically add players to the list of listeners
 */
@Aspect
@Component
public class GameListenerAspect {

    private final GameEventPublisher publisher;

    public GameListenerAspect(GameEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Pointcut("@annotation(com.games.annotation.GameListener)")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        GameGenerator generator = (GameGenerator) joinPoint.proceed();
        List<Player> players = generator.getPlayers();
        players.forEach(player -> publisher.register(player, 0));
        return generator;
    }
}
