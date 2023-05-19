package com.triviagame.triviagame.controller.impl;

import com.triviagame.triviagame.controller.GameController;
import com.triviagame.triviagame.controller.exception.InvalidInputException;
import com.triviagame.triviagame.model.GameManager;
import com.triviagame.triviagame.view.impl.GameConsoleView;

import java.util.List;
import java.util.Scanner;

public class GameConsoleController extends GameController {
    private final GameConsoleView gameConsoleView;
    private final Scanner userInputScanner;

    public GameConsoleController(GameManager gameManager, GameConsoleView gameConsoleView) {
        super(gameManager);
        this.gameConsoleView = gameConsoleView;
        this.userInputScanner = new Scanner(System.in);
    }

    @Override
    protected int getInitNumberOfTriviaQuestionsUserInput(int minNumberOfQuestions,
                                                          int maxNumberOfQuestions) {
        int numberOfQuestions = -1;
        boolean isUserInputValid = false;
        String initMessage = String.format("Enter the number of trivia questions (%d-%d):",
                minNumberOfQuestions, maxNumberOfQuestions);

        while (!isUserInputValid) {
            gameConsoleView.displayInfoMessage(initMessage);
            String userInput = getUserTextConsoleInput();

            try {
                isUserInputValid = isInitialNumberOfTriviaQuestionsUserInputValid(
                        userInput, minNumberOfQuestions, maxNumberOfQuestions);

                numberOfQuestions = Integer.parseInt(userInput);

            } catch (InvalidInputException ex) {
                gameConsoleView.displayInfoMessage(ex.getMessage());
            }
        }

        return numberOfQuestions;
    }

    @Override
    protected int getTriviaCategoryIDUserInput(int randomCategoryID, int minCategoryID,
                                               int maxCategoryID) {
        int categoryID = -1;
        boolean isUserInputValid = false;
        String initMessage = String.format("Enter the ordinal number of trivia category" +
                        " you want to select (%d-%d). Enter %d to select random trivia categories.",
                minCategoryID, maxCategoryID, randomCategoryID);

        while (!isUserInputValid) {
            gameConsoleView.displayInfoMessage(initMessage);
            String userInput = getUserTextConsoleInput();

            try {
                isUserInputValid = isCategoryIDUserInputValid(
                        userInput, randomCategoryID, minCategoryID, maxCategoryID);

                categoryID = Integer.parseInt(userInput);

            } catch (InvalidInputException ex) {
                gameConsoleView.displayInfoMessage(ex.getMessage());
            }
        }

        return categoryID;
    }

    @Override
    protected String getTriviaDifficultyUserInput(List<String> difficultyOptions) {
        String difficulty = null;
        boolean isUserInputValid = false;
        StringBuilder initMessage = new StringBuilder(
                "Enter the trivia difficulty you want to select:\n");

        int ordinalNumber = 1;
        for (String difficultyOption : difficultyOptions) {
            initMessage.append(String.format("\t%d) %s\n", ordinalNumber, difficultyOption));
            ++ordinalNumber;
        }

        while (!isUserInputValid) {
            gameConsoleView.displayInfoMessage(String.valueOf(initMessage));
            String userInput = getUserTextConsoleInput();

            try {
                isUserInputValid = isDifficultyLevelUserInputValid(
                        userInput, difficultyOptions);

                difficulty = userInput;

            } catch (InvalidInputException ex) {
                gameConsoleView.displayInfoMessage(ex.getMessage());
            }
        }

        return difficulty;
    }

    @Override
    protected String getTriviaTypeUserInput(List<String> triviaTypeOptions) {
        String triviaType = null;
        boolean isUserInputValid = false;
        StringBuilder initMessage = new StringBuilder(
                "Enter the trivia type you want to select:\n");

        int ordinalNumber = 1;
        for (String triviaTypeOption : triviaTypeOptions) {
            initMessage.append(String.format("\t%d) %s\n", ordinalNumber, triviaTypeOption));
            ++ordinalNumber;
        }

        while (!isUserInputValid) {
            gameConsoleView.displayInfoMessage(String.valueOf(initMessage));
            String userInput = getUserTextConsoleInput();

            try {
                isUserInputValid = isTriviaTypeUserInputValid(
                        userInput, triviaTypeOptions);

                triviaType = userInput;

            } catch (InvalidInputException ex) {
                gameConsoleView.displayInfoMessage(ex.getMessage());
            }
        }

        return triviaType;
    }

    @Override
    protected void handleUserAnswerInput() {
        String userAnswer = getUserAnswer();
        boolean isAnswerCorrect;

        try {
            int userNumberAnswer = Integer.parseInt(userAnswer);
            isAnswerCorrect = checkUserNumberAnswer(
                    this.gameManager.getCurrentTriviaList().getCurrentTrivia()
                            .getCorrectNumberAnswer(), userNumberAnswer);

        } catch (NumberFormatException e) {
            isAnswerCorrect = checkUserTextAnswer(
                    this.gameManager.getCurrentTriviaList().getCurrentTrivia()
                            .getCorrectTextAnswer(), userAnswer);
        }

        if (isAnswerCorrect) {
            addScore(this.gameManager.getCurrentTriviaList().getCurrentTrivia().getDifficulty());
        }

    }

    @Override
    protected String getUserAnswer() {
        return getUserTextConsoleInput();
    }

    @Override
    protected void showCurrentTrivia() {
        gameConsoleView.displayCurrentGameProgress(
                gameManager.getCurrentTriviaList().getCurrentTriviaOrdinalNumber(),
                gameManager.getCurrentTriviaList().getInitialNumberOfTrivia(),
                gameManager.getCurrentScore());

        gameConsoleView.displayCurrentTrivia(
                gameManager.getCurrentTriviaList().getCurrentTrivia().getQuestion(),
                gameManager.getCurrentTriviaList().getCurrentTrivia().getShuffledAnswers()
        );
    }

    @Override
    protected void showGameSummary() {
        gameConsoleView.displayGameSummary(
                "GOOD JOB!",
                super.gameManager.getCurrentScore(),
                0
        );
    }

    @Override
    protected void showErrorMessage() {

    }

    private String getUserTextConsoleInput() {
        return this.userInputScanner.nextLine().trim();
    }
}
