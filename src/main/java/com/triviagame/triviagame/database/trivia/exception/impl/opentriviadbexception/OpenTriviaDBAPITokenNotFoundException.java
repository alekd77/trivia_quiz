package com.triviagame.triviagame.database.trivia.exception.impl.opentriviadbexception;

import com.triviagame.triviagame.database.trivia.exception.impl.opentriviadbexception.OpenTriviaDBAPIException;

public class OpenTriviaDBAPITokenNotFoundException extends OpenTriviaDBAPIException {
    public OpenTriviaDBAPITokenNotFoundException() {
        super("Session Token does not exist.");
    }

    public OpenTriviaDBAPITokenNotFoundException(String message) {
        super(message);
    }
}
