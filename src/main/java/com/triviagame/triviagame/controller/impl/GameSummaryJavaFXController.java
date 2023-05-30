package com.triviagame.triviagame.controller.impl;

import com.triviagame.triviagame.AppLogger;
import com.triviagame.triviagame.JavaFXAppManager;
import com.triviagame.triviagame.controller.JavaFXAbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameSummaryJavaFXController extends JavaFXAbstractController implements Initializable {
    @FXML
    private Label gameSummaryMessageLabel;
    @FXML
    private Label gameScoreLabel;
    @FXML
    private Label gameTimeLabel;
    @FXML
    private Button exitButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int score = JavaFXAppManager.getGameManager().getCurrentScore();
        long time = JavaFXAppManager.getGameManager().getFinalGameTime();
        long minutes = time / (60 * 1000);
        long seconds = (time / 1000) % 60;

        gameSummaryMessageLabel.setText("GOOD JOB!");
        gameScoreLabel.setText(String.format("Score: %d", score));
        gameTimeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    public void onExitButtonClick(ActionEvent event) {
        try {
            switchToNewScene(event, "/main-app-view.fxml");
        } catch (IOException ex) {
            String errorMessage = String.format(
                    "Failed to go back to Menu due to: %s", ex.getMessage());
            AppLogger.error(errorMessage);
            showError(errorMessage);
        }
    }
}
