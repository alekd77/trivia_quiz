package com.triviagame.triviagame.view.impl;

import com.triviagame.triviagame.view.GameView;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class GameConsoleView implements GameView {
    @Override
    public void displayInfoMessage(String message) {
        if (message.isEmpty()) {
            System.out.println();
        } else {
            System.out.printf("\n%s\n", message);
        }
    }

    @Override
    public void displayCurrentGameProgress(int currentTriviaOrdinalNumber,
                                           int initialNumberOfTrivia,
                                           int currentScore) {
        System.out.println("********************");
        System.out.printf("Trivia:\t%d/%d\n", currentTriviaOrdinalNumber, initialNumberOfTrivia);
        System.out.printf("Current score:\t%d\n", currentScore);
        System.out.println("********************\n");
    }

    @Override
    public void displayCurrentTrivia(String question, List<String> answers) {
        System.out.printf("Question:\n\t%s\n\n", StringEscapeUtils.unescapeHtml4(question));

        int answerOrdinalNumber = 1;
        System.out.println("Answers:");
        for (String answer : answers) {
            System.out.printf("\t%d) %s\n", answerOrdinalNumber,
                    StringEscapeUtils.unescapeHtml4(answer)
            );
            ++answerOrdinalNumber;
        }

        System.out.print("\nYour answer:\n\t");
    }

    @Override
    public void displayGameSummary(String gameResultDescription, int score,
                                   int gameTimeInSeconds) {
        System.out.println("********************");
        System.out.printf("**** %s ****\n", gameResultDescription);
        System.out.printf("Your score: %d\n", score);
        System.out.printf("Time: %d\n", gameTimeInSeconds);
        System.out.println("********************");
    }
}
