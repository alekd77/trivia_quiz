package com.triviagame.triviagame.controller.impl;

import com.triviagame.triviagame.AppLogger;
import com.triviagame.triviagame.JavaFXAppManager;
import com.triviagame.triviagame.controller.JavaFXAbstractController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainAppJavaFXController extends JavaFXAbstractController {
    @FXML
    public Button startNewGameButton;
    @FXML
    private Button exitButton;

    public void onStartNewGameButtonClick(ActionEvent event) {
        if (JavaFXAppManager.getGameManager() == null) {
            String errorMessage = "Initializing game manager failed";
            AppLogger.error(errorMessage);
            showError(errorMessage);
            Platform.exit();
        }

        try {
            switchToGameSetupParamsSelectionScene(event);
        } catch (IOException ex) {
            showError(ex.getMessage());
        }
    }

    public void onExitButtonClick(ActionEvent event) {
        Platform.exit();
    }

    private void switchToGameSetupParamsSelectionScene(ActionEvent event) throws IOException {
        switchToNewScene(event, "/game-setup-selection-view.fxml");
    }

    private void loadInitDebugGameSetup() {
//        GameSetupParams gameSetup = new GameSetupParams(
//                10, 0, "random",
//                "random","default");
//
//        JavaFXAppManager.getGameManager().setGameSetupParams(gameSetup);
//
//
//
//        if (AppConfig.getInstance().isDebugModeEnabled()) {
//            loadInitDebugGameSetup();
//            switchToNewGameScene(event);
//            AppLogger.debug("New game started");
//        } else {
//            // switchToGameSetupParamsSelectionScene(event);
//            // AppLogger.debug("Game setup parameters selection");
//            String errorMessage = "Custom new game not supported yet";
//            AppLogger.error(errorMessage);
//            showError(errorMessage);
//        }
    }
}
