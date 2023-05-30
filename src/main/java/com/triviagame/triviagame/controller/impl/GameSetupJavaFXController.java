package com.triviagame.triviagame.controller.impl;

import com.triviagame.triviagame.AppConfig;
import com.triviagame.triviagame.AppLogger;
import com.triviagame.triviagame.JavaFXAppManager;
import com.triviagame.triviagame.controller.JavaFXAbstractController;
import com.triviagame.triviagame.controller.exception.InvalidInputException;
import com.triviagame.triviagame.database.trivia.exception.TriviaDBException;
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
        difficultyChoiceBox.getItems().addAll(OpenTriviaDBConstants.DIFFICULTY_LEVELS);
        triviaTypeChoiceBox.getItems().addAll(OpenTriviaDBConstants.TRIVIA_TYPES);
    }

    public void onSubmitButtonClick(ActionEvent event) {
        try {
            if (AppConfig.getInstance().isDebugModeEnabled()) {
                loadInitGameSetup(JavaFXAppManager.getGameManager(), getDebugGameSetupParams());
            } else {
                throw new IOException("Custom game setup not supported yet");
            }

            JavaFXAppManager.getGameManager().setCurrentGameState(PLAYING);
            switchToNewGameScene(event);

        } catch (TriviaDBException | IOException ex) {
            String errorMessage = String.format(
                    "Failed to load init game setup due to: %s", ex.getMessage());
            AppLogger.error(errorMessage);
            showError(errorMessage);
        }
    }

    private void loadInitGameSetup(GameManager gameManager,
                                   GameSetupParams gameSetupParams) throws TriviaDBException {
        gameManager.setGameSetupParams(gameSetupParams);
        gameManager.setCurrentTriviaList(
                gameManager.getTriviaDBManager().createNewTriviaList(
                        gameManager.getGameSetupParams()));
        gameManager.setCurrentScore(0);
        gameManager.setCurrentGameState(PLAYING);
    }

    private GameSetupParams getDebugGameSetupParams() {
        return new GameSetupParams(10, 0,
                "random", "random","default");
    }

    private boolean isNumberOfTriviaUserInputValid(
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

    private void switchToNewGameScene(ActionEvent event) throws IOException {
        switchToNewScene(event, "/game-play-view.fxml");
    }
}
