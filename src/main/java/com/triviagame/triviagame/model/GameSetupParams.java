package com.triviagame.triviagame.model;

public record GameSetupParams(
        int numberOfQuestions,
        int categoryID,
        String difficulty,
        String triviaType,
        String encoding) {
}
