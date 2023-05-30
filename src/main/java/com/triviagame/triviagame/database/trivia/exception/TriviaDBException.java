package com.triviagame.triviagame.database.trivia.exception;

public class TriviaDBException extends Exception {
    public TriviaDBException() {
        super("Unknown reason trivia database exception");
    }

    public TriviaDBException(String message) {
        super(message);
    }
}
