package com.priceline.chutes.service;

import com.priceline.chutes.enums.RuleType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author lzn
 * @Description Chute rules: allow players to slide from the top to the bottom
 */
@Service
public class ChuteStrategy implements RuleStrategy {
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public Map<Integer, Integer> generateGameRule(@NotNull int[] board, @NotNull int count) {
        Map<Integer, Integer> result = new HashMap<>(count);
        int boardSize = board.length;
        for (int i = 0; i < count; ) {
            int start = random.nextInt(boardSize - 1) + 1;
            int end = random.nextInt(start);
            if (board[start] == 0) {
                result.put(start, end);
                board[start] = end;
                board[end] = start;
                i++;
            }
        }
        return result;
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.CHUTE;
    }
}
