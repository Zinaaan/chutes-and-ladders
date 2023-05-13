package com.priceline.chutes.builder;

import com.priceline.chutes.entity.Player;
import com.priceline.chutes.enums.GameState;
import com.priceline.chutes.exceptions.InvalidBoardSizeException;
import com.priceline.chutes.service.RollingStrategy;
import com.priceline.chutes.service.RuleStrategy;
import com.priceline.chutes.service.TurnStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzn
 * @Description GameService generator, integrated with Builder pattern
 */
public class GameGenerator {

    private GameState gameState;
    private final int chuteCount;
    private final int laddersCount;
    private int playerWithTurn;
    private final int[] board;
    private List<Player> players;
    private final TurnStrategy turnStrategy;
    private final RollingStrategy rollingStrategy;
    private final List<RuleStrategy> rules;
    /**
     * store each player current position
     */
    private final Map<Player, Integer> playerLocations;
    /**
     * chutes position <start, end>
     */
    private Map<Integer, Integer> chuteLocations;
    /**
     * ladders position <start, end>
     */
    private Map<Integer, Integer> ladderLocations;

    private GameGenerator(Builder builder) {
        this.gameState = GameState.WAITING;
        this.chuteCount = builder.chute;
        this.laddersCount = builder.ladder;
        this.players = builder.players;
        this.turnStrategy = builder.turnStrategy;
        this.rollingStrategy = builder.rollingStrategy;
        this.rules = builder.rules;
        playerLocations = new HashMap<>();
        chuteLocations = new HashMap<>();
        ladderLocations = new HashMap<>();
        players.forEach(player -> playerLocations.put(player, 0));
        playerWithTurn = turnStrategy.getNext(players.size());
        board = new int[builder.boardWidth * builder.boardHeight];
    }

    public int[] getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getChuteCount() {
        return chuteCount;
    }

    public int getLaddersCount() {
        return laddersCount;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public TurnStrategy getTurnStrategy() {
        return turnStrategy;
    }

    public RollingStrategy getRollingStrategy() {
        return rollingStrategy;
    }

    public List<RuleStrategy> getRules() {
        return rules;
    }

    public int getPlayerWithTurn() {
        return playerWithTurn;
    }

    public void setPlayerWithTurn(int playerWithTurn) {
        this.playerWithTurn = playerWithTurn;
    }

    public Map<Player, Integer> getPlayerLocations() {
        return playerLocations;
    }

    public Map<Integer, Integer> getChuteLocations() {
        return chuteLocations;
    }

    public void setChuteLocations(Map<Integer, Integer> chuteLocations) {
        this.chuteLocations = chuteLocations;
    }

    public Map<Integer, Integer> getLadderLocations() {
        return ladderLocations;
    }

    public void setLadderLocations(Map<Integer, Integer> ladderLocations) {
        this.ladderLocations = ladderLocations;
    }

    public static class Builder {
        private final int chute, ladder;
        private final int boardWidth, boardHeight;
        private List<Player> players;
        private TurnStrategy turnStrategy;
        private RollingStrategy rollingStrategy;
        private List<RuleStrategy> rules;

        public Builder(int boardWidth, int boardHeight, int chute, int ladder) {
            this.chute = chute;
            this.ladder = ladder;
            this.boardWidth = boardWidth;
            this.boardHeight = boardHeight;
        }

        public Builder withPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder withRuleStrategies(List<RuleStrategy> rules) {
            this.rules = rules;
            return this;
        }

        public Builder withRollingStrategy(RollingStrategy rollingStrategy) {
            this.rollingStrategy = rollingStrategy;
            return this;
        }

        public Builder withTurnStrategy(TurnStrategy turnStrategy) {
            this.turnStrategy = turnStrategy;
            return this;
        }

        public GameGenerator build() {
            int minWidth = 3;
            int minHeight = 3;
            if (boardWidth < minWidth || boardHeight < minHeight) {
                throw new InvalidBoardSizeException("Board size should be at least 3x3");
            }
            return new GameGenerator(this);
        }
    }
}
