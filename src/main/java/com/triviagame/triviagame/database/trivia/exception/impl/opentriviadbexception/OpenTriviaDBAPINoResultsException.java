package com.triviagame.triviagame.database.trivia.exception.impl.opentriviadbexception;

import com.triviagame.triviagame.database.trivia.exception.impl.opentriviadbexception.OpenTriviaDBAPIException;

public class OpenTriviaDBAPINoResultsException extends OpenTriviaDBAPIException {

    public OpenTriviaDBAPINoResultsException() {
        super("The API doesn't have enough questions for your query." +
                " (Ex. Asking for 50 Questions in a Category that only has 20.)");
    }
    public OpenTriviaDBAPINoResultsException(String message) {
        super(message);
    }
}
