package com.triviagame.triviagame.controller;

import com.triviagame.triviagame.AppLogger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class JavaFXAbstractController {
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void switchToNewScene(ActionEvent event, String sceneViewFXMLFile) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(sceneViewFXMLFile)));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException | NullPointerException ex) {
            String errorMessage = String.format("Failed to load %s file", sceneViewFXMLFile);
            AppLogger.error(errorMessage);
            throw new IOException(errorMessage);
        }
    }

    public void showError(String errorMessage) {
        // Show a pop-up window with the error message
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void showError(String errorMessage, String errorTitle) {
        // Show a pop-up window with the error message and custom error title
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
        alert.setTitle(errorTitle);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
