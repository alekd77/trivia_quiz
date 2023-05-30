package com.triviagame.triviagame.database.trivia.exception.impl.opentriviadbexception;

import com.triviagame.triviagame.database.trivia.exception.TriviaDBException;

public class OpenTriviaDBAPIException extends TriviaDBException {
    public OpenTriviaDBAPIException() {
        super("Unknown reason OpenTriviaDBAPIException");
    }

    public OpenTriviaDBAPIException(String message) {
        super(message);
    }
}

