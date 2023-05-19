package com.triviagame.triviagame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Trivia {
    @JsonProperty("category")
    private String category;

    @JsonProperty("type")
    private String type;

    @JsonProperty("difficulty")
    private String difficulty;

    @JsonProperty("question")
    private String question;

    @JsonProperty("correct_answer")
    private String correctTextAnswer;

    @JsonIgnore
    private int correctNumberAnswer;

    @JsonProperty("incorrect_answers")
    private List<String> incorrectAnswers;

    @JsonIgnore
    private int ordinalNumber;

    @JsonIgnore
    private List<String> shuffledAnswers;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectTextAnswer() {
        return correctTextAnswer;
    }

    public void setCorrectTextAnswer(String correctTextAnswer) {
        this.correctTextAnswer = correctTextAnswer;
    }

    public int getCorrectNumberAnswer() {
        return correctNumberAnswer;
    }

    public void setCorrectNumberAnswer(int correctNumberAnswer) {
        this.correctNumberAnswer = correctNumberAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public int getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public List<String> getShuffledAnswers() {
        return shuffledAnswers;
    }

    @Override
    public String toString() {
        return "Trivia{" +
                "category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", question='" + question + '\'' +
                ", correctTextAnswer='" + correctTextAnswer + '\'' +
                ", incorrectAnswers=" + incorrectAnswers +
                ", ordinalNumber=" + ordinalNumber +
                '}';
    }

    public void shuffleAnswers() {
        this.shuffledAnswers = new ArrayList<>();
        this.shuffledAnswers.add(this.correctTextAnswer);
        this.shuffledAnswers.addAll(this.incorrectAnswers);
        Collections.shuffle(shuffledAnswers);

        this.correctNumberAnswer = findCorrectNumberAnswer(this.shuffledAnswers);
    }

    private int findCorrectNumberAnswer(List<String> shuffledAnswers) {
        int correctNumberAnswer = 1;
        for (String answer : shuffledAnswers) {
            if (answer.equals(this.correctTextAnswer)) {
                break;
            }

            ++correctNumberAnswer;
        }

        if (!correctTextAnswer.equalsIgnoreCase(shuffledAnswers.get(correctNumberAnswer - 1))) {
            throw new RuntimeException("Failed to find correct number answer");
        }

        this.correctNumberAnswer = correctNumberAnswer;

        return correctNumberAnswer;
    }
}
