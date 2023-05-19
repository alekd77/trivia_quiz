package com.triviagame.triviagame.database.trivia.opentriviadb.exception;

public class OpenTriviaDBAPITokenEmptyException extends OpenTriviaDBAPIException {
    public OpenTriviaDBAPITokenEmptyException() {
        super("Session Token has returned all possible questions for the specified query." +
                " Resetting the Token is necessary.");
    }

    public OpenTriviaDBAPITokenEmptyException(String message) {
        super(message);
    }
}
