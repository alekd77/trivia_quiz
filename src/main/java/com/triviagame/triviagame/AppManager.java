package com.triviagame.triviagame;

import com.triviagame.triviagame.controller.impl.GameConsoleController;
import com.triviagame.triviagame.database.trivia.TriviaDBManager;
import com.triviagame.triviagame.database.trivia.opentriviadb.OpenTriviaDBConstants;
import com.triviagame.triviagame.database.trivia.opentriviadb.OpenTriviaDBManager;
import com.triviagame.triviagame.model.GameManager;
import com.triviagame.triviagame.model.GameState;
import com.triviagame.triviagame.view.impl.GameConsoleView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppManager extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppManager.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        AppController appController = fxmlLoader.getController();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();

        TriviaDBManager triviaDBManager = new OpenTriviaDBManager();
        GameManager gameManager = new GameManager(triviaDBManager);
        GameConsoleView gameConsoleView = new GameConsoleView();
        GameConsoleController gameConsoleController = new GameConsoleController(
                gameManager, gameConsoleView);

        gameManager.setCurrentGameState(GameState.GAME_SETUP_PARAMS_SELECTION);

        gameConsoleController.handleGameSetupInitialization();
        gameConsoleController.handleGame();
        gameConsoleController.handleGameSummary();
    }
}