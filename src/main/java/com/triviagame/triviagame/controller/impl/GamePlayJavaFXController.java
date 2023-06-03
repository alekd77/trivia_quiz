package com.triviagame.triviagame.controller.impl;

import com.triviagame.triviagame.AppLogger;
import com.triviagame.triviagame.JavaFXAppManager;
import com.triviagame.triviagame.controller.JavaFXAbstractController;
import com.triviagame.triviagame.model.TriviaList;
import com.triviagame.triviagame.model.Trivia;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GamePlayJavaFXController extends JavaFXAbstractController implements Initializable {
    @FXML
    private Label triviaQuestionLabel;
    @FXML
    private GridPane triviaAnswerButtonsGridPane;
    @FXML
    private Label triviaNoLabel;
    @FXML
    private Label triviaCategoryLabel;
    @FXML
    private Label triviaDifficultyLabel;
    @FXML
    private Label currentScoreLabel;
    @FXML
    private Label elapsedTimeLabel;
    @FXML
    private Button nextTriviaButton;
    private final TriviaList currentTriviaList;
    private Trivia currentTrivia;
    private long startTime;
    private long finalTime;
    private boolean isGameFinished;

    public GamePlayJavaFXController() {
        currentTriviaList = JavaFXAppManager.getGameManager().getCurrentTriviaList();
        currentTrivia = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadNextTrivia();

        startTime = System.currentTimeMillis();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateElapsedTimeLabel();

                if (isGameFinished) {
                    stop();
                    finalTime = System.currentTimeMillis() - startTime;
                }
            }
        };

        timer.start();
    }

    private void loadNextTrivia() {
        try {
            currentTriviaList.loadNextTrivia();
            currentTrivia = currentTriviaList.getCurrentTrivia();

            if (currentTriviaList.getNumberOfLeftTrivia() == 0) {
                isGameFinished = true;
            } else if (currentTriviaList.getNumberOfLeftTrivia() < 0) {
                throw new IOException("SOMETHING GONE WRONG");
            }

            updateLabels();

        } catch (IOException ex) {
            String errorMessage = String.format(
                    "Failed to load next trivia due to: %s", ex.getMessage());
            AppLogger.error(errorMessage);
            showError(errorMessage);
        }
    }

    private void updateLabels() {
        updateTriviaQuestionLabel();
        updateTriviaAnswerButtonsGridPane();
        updateTriviaDescriptionLabels();
        updateCurrentGameProgressLabels();
    }

    private void updateTriviaQuestionLabel() {
        String questionText = StringEscapeUtils.unescapeHtml4(
                currentTrivia.getQuestion());

        triviaQuestionLabel.setText(questionText);
    }

    private void updateTriviaAnswerButtonsGridPane() {
        triviaAnswerButtonsGridPane.getChildren().clear();

        int columnIdx = 0;
        int rowIdx = 0;

        for (String answer : currentTrivia.getShuffledAnswers()) {
            String answerText = StringEscapeUtils.unescapeHtml4(answer);
            Button answerButton = new Button(answerText);

            answerButton.setOnAction(event -> onAnswerButtonClick(event));
            triviaAnswerButtonsGridPane.add(answerButton, columnIdx, rowIdx);
            ++rowIdx;
        }
    }

    @FXML
    private void onAnswerButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String selectedAnswer = clickedButton.getText();
        String correctAnswer = StringEscapeUtils.unescapeHtml4(
                currentTrivia.getCorrectAnswer());

        if (isSelectedAnswerCorrect(selectedAnswer, correctAnswer)) {
            addScore(currentTrivia.getDifficulty());
            updateCurrentScoreLabel();
            markCorrectAnswerButton(clickedButton);
        } else {
            markWrongAnswerButton(clickedButton);
            Button correctAnswerButton = getCorrectAnswerButton(correctAnswer);

            if (correctAnswerButton != null) {
                markCorrectAnswerButton(correctAnswerButton);
            }
        }

        for (Node node : triviaAnswerButtonsGridPane.getChildren()) {
            Button button = (Button) node;
            button.setDisable(true);
        }

        if (isGameFinished) {
            handleGameFinished(event);
        } else {
            nextTriviaButton.setVisible(true);
            nextTriviaButton.setDisable(false);
        }
    }

    @FXML
    private void onNextTriviaButtonClicked(ActionEvent event) {
        loadNextTrivia();
        nextTriviaButton.setVisible(false);
        nextTriviaButton.setDisable(true);

        for (Node node : triviaAnswerButtonsGridPane.getChildren()) {
            Button button = (Button) node;
            button.setDisable(false);
        }
    }

    private void markCorrectAnswerButton(Button button) {
        button.setStyle("-fx-background-color: green;");
    }

    private void markWrongAnswerButton(Button button) {
        button.setStyle("-fx-background-color: red;");
    }

    private Button getCorrectAnswerButton(String correctAnswer) {
        for (Node node : triviaAnswerButtonsGridPane.getChildren()) {
            Button button = (Button) node;

            if (button.getText().equals(correctAnswer)) {
                return button;
            }
        }
        return null; // If no correct answer button is found
    }

    private void handleGameFinished(ActionEvent event) {
        JavaFXAppManager.getGameManager().setFinalGameTime(finalTime);
        try {
            switchToNewScene(event, "/game-summary-view.fxml");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private boolean isSelectedAnswerCorrect(String selectedAnswer, String correctAnswer) {
        return selectedAnswer.equalsIgnoreCase(correctAnswer);
    }

    private void addScore(String triviaDifficulty) {
        int currentScore = JavaFXAppManager.getGameManager().getCurrentScore();
        int scoreToAdd = calculatePointsToAdd(triviaDifficulty);

        JavaFXAppManager.getGameManager().setCurrentScore(currentScore + scoreToAdd);
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

    private void updateTriviaDescriptionLabels() {
        updateTriviaOrdinalNumberLabel();
        updateTriviaCategoryLabel();
        updateTriviaDifficultyLabel();
    }

    private void updateTriviaOrdinalNumberLabel() {
        int triviaNo = currentTriviaList.getCurrentTriviaOrdinalNumber();
        int totalTriviaCount = currentTriviaList.getInitialNumberOfTrivia();
        String triviaNoText = String.format(
                "Trivia No: %d/%d", triviaNo, totalTriviaCount);

        triviaNoLabel.setText(triviaNoText);
    }

    private void updateTriviaCategoryLabel() {
        String category = currentTrivia.getCategory();
        String categoryText = String.format("Category: %s", category);

        triviaCategoryLabel.setText(categoryText);
    }

    private void updateTriviaDifficultyLabel() {
        String difficulty = currentTrivia.getDifficulty();
        String difficultyText = String.format("Difficulty: %s", difficulty);

        triviaDifficultyLabel.setText(difficultyText);
    }

    private void updateCurrentGameProgressLabels() {
        updateCurrentScoreLabel();
        updateElapsedTimeLabel();
    }

    private void updateCurrentScoreLabel() {
        int currentScore = JavaFXAppManager.getGameManager().getCurrentScore();
        String scoreText = String.format("Score: %d", currentScore);

        currentScoreLabel.setText(scoreText);
    }

    private void updateElapsedTimeLabel() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        // Convert elapsed time to minutes and seconds
        long minutes = elapsedTime / (60 * 1000);
        long seconds = (elapsedTime / 1000) % 60;

        // Update the time label with the new value
        String elapsedTimeText = String.format("Time: %02d:%02d", minutes, seconds);
        elapsedTimeLabel.setText(elapsedTimeText);
    }
}
