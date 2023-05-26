package com.games.service;

import com.games.enums.RuleType;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author lzn
 * @Description Ladder rules: allow players to climb from the bottom to the top
 */
@Service
public class LadderStrategy implements RuleStrategy {
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public Map<Integer, Integer> generateGameRule(@NotNull int[] board, @NotNull int count) {
        Map<Integer, Integer> result = new HashMap<>(count);
        int boardSize = board.length;
        for (int i = 0; i < count; ) {
            int start = random.nextInt(boardSize - 1) + 1;
            int end = start + random.nextInt(boardSize - start) + 1;
            if (board[start] == 0) {
                result.put(start, end);
                board[start] = end;
                i++;
            }
        }

        return result;
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.LADDER;
    }
}
