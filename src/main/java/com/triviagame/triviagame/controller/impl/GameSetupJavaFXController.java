package com.triviagame.triviagame.controller.impl;

import com.triviagame.triviagame.AppConfig;
import com.triviagame.triviagame.AppLogger;
import com.triviagame.triviagame.JavaFXAppManager;
import com.triviagame.triviagame.controller.JavaFXAbstractController;
import com.triviagame.triviagame.controller.exception.InvalidInputException;
import com.triviagame.triviagame.database.trivia.opentriviadb.OpenTriviaDBConstants;
import com.triviagame.triviagame.model.GameManager;
import com.triviagame.triviagame.model.GameSetupParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.triviagame.triviagame.model.GameState.PLAYING;

public class GameSetupJavaFXController extends JavaFXAbstractController implements Initializable {
    @FXML
    private TextField numberOfTriviaTextField;
    @FXML
    private ChoiceBox<String> difficultyChoiceBox;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private ChoiceBox<String> triviaTypeChoiceBox;
    @FXML
    private Button submitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryChoiceBox.getItems().addAll(OpenTriviaDBConstants.CATEGORY_ID_TO_NAME_MAP.values());
        difficultyChoiceBox.getItems().addAll(OpenTriviaDBConstants.DifficultyLevels.valuesAsString());
        triviaTypeChoiceBox.getItems().addAll(OpenTriviaDBConstants.TriviaTypes.valuesAsString());
    }

    public void onSubmitButtonClick(ActionEvent event) {
        try {
            if (AppConfig.getInstance().isDebugModeEnabled()) {
                loadInitGameSetup(JavaFXAppManager.getGameManager(), getDebugGameSetupParams());
            } else {
                loadInitGameSetup(JavaFXAppManager.getGameManager(), getGameSetupParamsUserInput());
            }

            JavaFXAppManager.getGameManager().setCurrentGameState(PLAYING);
            switchToNewGameScene(event);

        } catch (InvalidInputException ex) {
            showError(ex.getMessage());

        } catch (IOException ex) {
            String errorMessage = String.format(
                    "Failed to load init game setup due to: %s", ex.getMessage());
            AppLogger.error(errorMessage);
            showError(errorMessage);
        }
    }

    private void loadInitGameSetup(GameManager gameManager,
                                   GameSetupParams gameSetupParams) throws IOException {
        gameManager.setGameSetupParams(gameSetupParams);
        gameManager.setCurrentTriviaList(
                gameManager.getTriviaDBManager().createNewTriviaList(
                        gameManager.getGameSetupParams()));
        gameManager.setCurrentScore(0);
        gameManager.setCurrentGameState(PLAYING);
    }

    private GameSetupParams getGameSetupParamsUserInput() throws InvalidInputException {
        int numberOfQuestions = getNumberOfTriviaQuestionsUserInput();
        int categoryID = getTriviaCategoryIDUserInput();
        String difficultyLevel = getTriviaDifficultyLevelUserInput();
        String triviaType = getTriviaTypeUserInput();
        String encoding = "default";

        return new GameSetupParams(numberOfQuestions, categoryID, difficultyLevel, triviaType, encoding);
    }

    private GameSetupParams getDebugGameSetupParams() {
        return new GameSetupParams(10, 0,
                "random", "random","default");
    }

    private int getNumberOfTriviaQuestionsUserInput() throws InvalidInputException {
        String userInput = numberOfTriviaTextField.getText();

        if (userInput.isEmpty()) {
            throw new InvalidInputException("Number of trivia questions is not specified");
        }

        try {
            int numberOfQuestions = Integer.parseInt(userInput);

            if (numberOfQuestions < OpenTriviaDBConstants.MIN_NUMBER_OF_QUESTIONS) {
                throw new InvalidInputException(String.format(
                        "The number of questions must be greater than %d.",
                        OpenTriviaDBConstants.MIN_NUMBER_OF_QUESTIONS));
            }

            if (numberOfQuestions > OpenTriviaDBConstants.MAX_NUMBER_OF_QUESTIONS) {
                throw new InvalidInputException(String.format(
                        "The number of questions cannot exceed %d.",
                        OpenTriviaDBConstants.MAX_NUMBER_OF_QUESTIONS));
            }

            return numberOfQuestions;

        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Invalid number of questions." +
                    " Argument passed in is not an integer. (Ex. Amount = Five)");
        }
    }

    private int getTriviaCategoryIDUserInput() throws InvalidInputException {
        String userInput = categoryChoiceBox.getValue();

        if (userInput == null) {
            throw new InvalidInputException("Trivia category not specified");
        }

        return OpenTriviaDBConstants.CATEGORY_NAME_TO_ID_MAP.get(userInput);
    }

    private String getTriviaDifficultyLevelUserInput() throws InvalidInputException {
        String userInput = difficultyChoiceBox.getValue();

        if (userInput == null) {
            throw new InvalidInputException("Trivia difficulty level not specified");
        }

        return switch (userInput) {
            case "RANDOM" -> "random";
            case "EASY" -> "easy";
            case "MEDIUM" -> "medium";
            case "HARD" -> "hard";
            default -> throw new InvalidInputException("Unknown trivia difficulty chosen");
        };
    }

    private String getTriviaTypeUserInput() throws InvalidInputException {
        String userInput = triviaTypeChoiceBox.getValue();

        if (userInput == null) {
            throw new InvalidInputException("Trivia type not specified");
        }

        return switch (userInput) {
            case "RANDOM" -> "random";
            case "MULTIPLE" -> "multiple";
            case "BOOLEAN" -> "boolean";
            default -> throw new InvalidInputException("Unknown trivia type chosen");
        };
    }

    private void switchToNewGameScene(ActionEvent event) throws IOException {
        switchToNewScene(event, "/game-play-view.fxml");
    }
}
