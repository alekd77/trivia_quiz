package com.triviagame.triviagame.model;

import com.triviagame.triviagame.database.trivia.opentriviadb.TriviaDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trivia {
    private final TriviaDTO triviaDTO;
    private final List<String> shuffledAnswers;

    public Trivia(TriviaDTO triviaDTO) {
        this.triviaDTO = triviaDTO;
        this.shuffledAnswers = createShuffledAnswersList();
    }

    public String getCategory() {
        return triviaDTO.getCategory();
    }

    public String getType() {
        return triviaDTO.getType();
    }

    public String getDifficulty() {
        return triviaDTO.getDifficulty();
    }

    public String getQuestion() {
        return triviaDTO.getQuestion();
    }

    public String getCorrectAnswer() {
        return triviaDTO.getCorrectAnswer();
    }

    public List<String> getIncorrectAnswers() {
        return triviaDTO.getIncorrectAnswers();
    }

    public List<String> getShuffledAnswers() {
        return shuffledAnswers;
    }

    private List<String> createShuffledAnswersList() {
        List<String> shuffledAnswers = new ArrayList<>();
        shuffledAnswers.add(getCorrectAnswer());
        shuffledAnswers.addAll(getIncorrectAnswers());
        Collections.shuffle(shuffledAnswers);

        return shuffledAnswers;
    }
}
