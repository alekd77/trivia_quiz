package com.triviagame.triviagame.database.trivia.opentriviadb.exception;

public class OpenTriviaDBAPITokenNotFoundException extends OpenTriviaDBAPIException {
    public OpenTriviaDBAPITokenNotFoundException() {
        super("Session Token does not exist.");
    }

    public OpenTriviaDBAPITokenNotFoundException(String message) {
        super(message);
    }
}
