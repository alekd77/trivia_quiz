package com.triviagame.triviagame;

import com.triviagame.triviagame.database.trivia.opentriviadb.OpenTriviaDBManager;
import com.triviagame.triviagame.model.GameManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXAppManager extends Application {
    @FXML
    private static Stage guiStage;
    private static GameManager gameManager;

    @FXML
    public static Stage getGuiStage() {
        return guiStage;
    }

    public static GameManager getGameManager() {
        return gameManager;
    }

    public static void setGameManager(GameManager gameManager) {
       JavaFXAppManager.gameManager = gameManager;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main-app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        guiStage = primaryStage;
        gameManager = new GameManager(new OpenTriviaDBManager());

        guiStage.setTitle("TriviaQuizApp");
        guiStage.setResizable(false);
        guiStage.setWidth(1200);
        guiStage.setHeight(800);
        guiStage.setScene(scene);
        guiStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
