package com.triviagame.triviagame.model;

public record GameSetupParams(
        int numberOfQuestions,
        int category,
        String difficulty,
        String triviaType,
        String encoding) {}
