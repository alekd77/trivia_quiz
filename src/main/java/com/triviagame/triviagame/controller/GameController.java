package com.triviagame.triviagame.controller;

import com.triviagame.triviagame.AppConfig;
import com.triviagame.triviagame.AppLogger;
import com.triviagame.triviagame.controller.exception.*;
import com.triviagame.triviagame.database.trivia.TriviaDBException;
import com.triviagame.triviagame.database.trivia.opentriviadb.OpenTriviaDBConstants;
import com.triviagame.triviagame.model.GameManager;
import com.triviagame.triviagame.model.GameSetupParams;
import com.triviagame.triviagame.model.GameState;

import java.util.List;

import static com.triviagame.triviagame.model.GameState.*;

public abstract class GameController {
    protected final GameManager gameManager;
    public GameController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void handleGameSetupInitialization() {
        if (gameManager.getCurrentGameState() != GAME_SETUP_PARAMS_SELECTION) {
            throw new RuntimeException(String.format(
                    "Failed to handle game setup initialization. Invalid game state: %s",
                    gameManager.getCurrentGameState()));
        }

        while (gameManager.getCurrentGameState() == GAME_SETUP_PARAMS_SELECTION) {
            GameSetupParams gameSetupParams = null;

            if (AppConfig.getInstance().isDebugModeEnabled()) {
                gameSetupParams = getInitDebugGameSetup();
            } else {
                gameSetupParams = getInitGameSetupUserInput();
            }

            try {
                if (loadInitGameSetup(gameSetupParams)) {
                    AppLogger.info("Successfully loaded init game setup");
                }

            } catch (TriviaDBException ex) {
                AppLogger.error(String.format(
                        "Failed to load init game setup due to: %s", ex.getMessage()));
                showErrorMessage();
            }
        }
    }

    public void handleGame() {
        if (gameManager.getCurrentGameState() != PLAYING) {
            throw new RuntimeException(String.format(
                    "Failed to handle game. Invalid game state: %s",
                    gameManager.getCurrentGameState()));
        }

        while (gameManager.getCurrentGameState() == PLAYING) {
            // loads new trivia from fixed list and set it as current trivia
            updateCurrentTrivia();

            showCurrentTrivia();

            handleUserAnswerInput();

            updateGameState();
        }
    }

    public void handleGameSummary() {
        showGameSummary();
    }

    protected boolean loadInitGameSetup(GameSetupParams gameSetupParams) throws TriviaDBException {
        gameManager.setGameSetupParams(gameSetupParams);
        gameManager.setCurrentTriviaList(
                gameManager.getTriviaDBManager().createNewTriviaList(
                        gameManager.getGameSetupParams()));
        gameManager.setCurrentScore(0);
        gameManager.setCurrentGameState(PLAYING);

        return true;
    }

    protected boolean isInitialNumberOfTriviaQuestionsUserInputValid(
            String userInput, int minNumberOfQuestions,
            int maxNumberOfQuestions) throws InvalidInputException {

        try {
            int numberOfQuestions = Integer.parseInt(userInput);

            if (numberOfQuestions < minNumberOfQuestions) {
                throw new InvalidInputException(String.format(
                        "The number of questions must be greater than %d.",
                        minNumberOfQuestions));
            }

            if (numberOfQuestions > maxNumberOfQuestions) {
                throw new InvalidInputException(String.format(
                        "The number of questions cannot exceed %d.",
                        maxNumberOfQuestions));
            }

        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Invalid number of questions." +
                    " Argument passed in is not an integer. (Ex. Amount = Five)");
        }

        return true;
    }

    protected boolean isCategoryIDUserInputValid(
            String userInput, int randomCategoryID,
            int minCategoryID, int maxCategoryID) throws InvalidInputException {

        try {
            int categoryID = Integer.parseInt(userInput);

            // throws an exception if the user category input
            // does not match any possible category IDs
            if ((categoryID != randomCategoryID)
                    && ((categoryID < minCategoryID)
                    || (categoryID > maxCategoryID))) {
                throw new InvalidInputException("Invalid category ID");
            }

        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Invalid category ID." +
                    " Argument passed in is not an integer. (Ex. Category = Five)");
        }

        return true;
    }

    protected boolean isDifficultyLevelUserInputValid(
            String userInput, List<String> difficultyOptions) throws InvalidInputException {

        for (String difficulty : difficultyOptions) {
            if (userInput.equalsIgnoreCase(difficulty)) {
                return true;
            }
        }

        throw new InvalidInputException("Invalid difficulty level");
    }

    protected boolean isTriviaTypeUserInputValid(
            String userInput, List<String> triviaTypeOptions) throws InvalidInputException {

        for (String type : triviaTypeOptions) {
            if (userInput.equalsIgnoreCase(type)) {
                return true;
            }
        }

        throw new InvalidInputException("Invalid difficulty level");
    }

    protected boolean validateGameSetupParams(GameSetupParams gameSetupParams) {
        if (gameSetupParams == null) {
            throw new RuntimeException("GameSetupParams not initialized");
        }

        return true;
    }

    protected boolean checkUserTextAnswer(String userAnswer, String correctAnswer) {
        return userAnswer.equalsIgnoreCase(correctAnswer);
    }

    protected boolean checkUserNumberAnswer(int userAnswer, int correctAnswer) {
        return userAnswer == correctAnswer;
    }

    protected void addScore(String triviaDifficulty) {
        if (gameManager.getCurrentGameState() != PLAYING) {
            throw new RuntimeException("Failed to add score. Game is finished.");
        }

        int currentScore = this.gameManager.getCurrentScore();
        int scoreToAdd = calculatePointsToAdd(triviaDifficulty);

        this.gameManager.setCurrentScore(currentScore + scoreToAdd);
    }

    private GameSetupParams getInitDebugGameSetup() {
        return new GameSetupParams(10, 0,
                "random", "random","default");
    }

    private GameSetupParams getInitGameSetupUserInput() {
        int numberOfTriviaQuestion = getInitNumberOfTriviaQuestionsUserInput(
                OpenTriviaDBConstants.MIN_NUMBER_OF_QUESTIONS,
                OpenTriviaDBConstants.MAX_NUMBER_OF_QUESTIONS
        );
        int categoryID = getTriviaCategoryIDUserInput(
                OpenTriviaDBConstants.RANDOM_CATEGORY_ID,
                OpenTriviaDBConstants.MIN_CATEGORY_ID,
                OpenTriviaDBConstants.MAX_CATEGORY_ID
        );
        String difficulty = getTriviaDifficultyUserInput(
                OpenTriviaDBConstants.DIFFICULTY_LEVELS
        );
        String triviaType = getTriviaTypeUserInput(
                OpenTriviaDBConstants.TRIVIA_TYPES
        );
        String encoding = "default";

        return new GameSetupParams(numberOfTriviaQuestion, categoryID,
                difficulty, triviaType, encoding);
    }

    private int calculatePointsToAdd(String triviaDifficulty) {
        return switch (triviaDifficulty) {
            case "easy" -> 100;
            case "medium" -> 200;
            case "hard" -> 300;
            default -> throw new RuntimeException(String.format(
                    "Failed to calculate points to add. Invalid trivia difficulty : %s",
                    triviaDifficulty));
        };
    }

    private void updateCurrentTrivia() {
        this.gameManager.getCurrentTriviaList().loadNextTrivia();
    }

    private void updateGameState() {
        GameState currentGameState = gameManager.getCurrentGameState();

        GameState newGameState = switch (currentGameState) {
            case MENU, SUMMARY-> null;
            case GAME_SETUP_PARAMS_SELECTION -> {
                if (gameManager.getGameSetupParams() == null) {
                    yield null;
                }

                yield PLAYING;
            }
            case PLAYING -> {
                if (gameManager.getCurrentTriviaList().getNumberOfLeftTrivia() > 0) {
                    yield null;
                }

                yield SUMMARY;
            }
        };

        if (newGameState != null) {
            gameManager.setCurrentGameState(newGameState);
        }
    }

    protected abstract int getInitNumberOfTriviaQuestionsUserInput(
            int minNumberOfQuestions, int maxNumberOfQuestions);
    protected abstract int getTriviaCategoryIDUserInput(
            int randomCategoryID, int minCategoryID, int maxCategoryID);
    protected abstract String getTriviaDifficultyUserInput(
            List<String> difficultyOptions);
    protected abstract String getTriviaTypeUserInput(
            List<String> triviaTypeOptions);
    protected abstract void handleUserAnswerInput();
    protected abstract String getUserAnswer();
    protected abstract void showCurrentTrivia();
    protected abstract void showGameSummary();
    protected abstract void showErrorMessage();
}
