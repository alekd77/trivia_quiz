package com.triviagame.triviagame.database.trivia.opentriviadb.exception;

import com.triviagame.triviagame.database.trivia.TriviaDBException;

public class OpenTriviaDBAPIException extends TriviaDBException {
    public OpenTriviaDBAPIException() {
        super("Unknown reason OpenTriviaDBAPIException");
    }

    public OpenTriviaDBAPIException(String message) {
        super(message);
    }
}

