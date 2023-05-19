package com.triviagame.triviagame.model;

import java.util.List;

public class TriviaList {
    private final List<Trivia> triviaList;
    private final int initialNumberOfTrivia;
    private Trivia currentTrivia;
    private int currentTriviaOrdinalNumber;
    private int numberOfLeftTrivia;

    public TriviaList(List<Trivia> triviaList) {
        this.triviaList = triviaList;
        this.initialNumberOfTrivia = triviaList.size();
        this.currentTrivia = null;
        this.currentTriviaOrdinalNumber = 0;
        this.numberOfLeftTrivia = this.initialNumberOfTrivia;
    }

    public List<Trivia> getTriviaList() {
        return triviaList;
    }

    public int getInitialNumberOfTrivia() {
        return initialNumberOfTrivia;
    }

    public Trivia getCurrentTrivia() {
        return currentTrivia;
    }

    public int getCurrentTriviaOrdinalNumber() {
        return currentTriviaOrdinalNumber;
    }

    public void setCurrentTriviaOrdinalNumber(int currentTriviaOrdinalNumber) {
        this.currentTriviaOrdinalNumber = currentTriviaOrdinalNumber;
    }

    public int getNumberOfLeftTrivia() {
        return numberOfLeftTrivia;
    }

    public void loadNextTrivia() {
        if (this.triviaList.isEmpty()) {
            if (numberOfLeftTrivia > 0) {
                throw new RuntimeException("Trivia list is empty");
            }
        }

        this.currentTrivia = this.triviaList.remove(0);
        this.currentTrivia.shuffleAnswers();
        ++currentTriviaOrdinalNumber;
        --numberOfLeftTrivia;
    }
}
