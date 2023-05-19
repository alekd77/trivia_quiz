package com.triviagame.triviagame.view;

import java.util.List;

public interface GameView {
    void displayInfoMessage(String message);
    void displayCurrentGameProgress(int currentTriviaOrdinalNumber,
                                    int initialNumberOfTrivia,
                                    int currentScore);
    void displayCurrentTrivia(String question, List<String> answers);
    void displayGameSummary(String gameResultDescription, int score,
                            int gameTimeInSeconds);
}
