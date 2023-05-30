package com.triviagame.triviagame.model;

import com.triviagame.triviagame.database.trivia.TriviaDBManager;

import static com.triviagame.triviagame.model.GameState.*;

public class GameManager {
    private final TriviaDBManager triviaDBManager;
    private GameState currentGameState;
    private GameSetupParams gameSetupParams;
    private TriviaList currentTriviaList;
    private int currentScore;
    private long finalGameTime;

    public GameManager(TriviaDBManager triviaDBManager) {
        this.triviaDBManager = triviaDBManager;
        this.currentGameState = MENU;
        this.gameSetupParams = null;
        this.currentTriviaList = null;
        this.currentScore = -1;
    }

    public TriviaDBManager getTriviaDBManager() {
        return triviaDBManager;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public GameSetupParams getGameSetupParams() {
        return gameSetupParams;
    }

    public void setGameSetupParams(GameSetupParams gameSetupParams) {
        this.gameSetupParams = gameSetupParams;
    }

    public TriviaList getCurrentTriviaList() {
        return currentTriviaList;
    }

    public void setCurrentTriviaList(TriviaList currentTriviaList) {
        this.currentTriviaList = currentTriviaList;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public long getFinalGameTime() {
        return finalGameTime;
    }

    public void setFinalGameTime(long finalGameTime) {
        this.finalGameTime = finalGameTime;
    }
}
