package com.triviagame.triviagame.database.trivia.opentriviadb.exception;

public class OpenTriviaDBAPIInvalidParameterException extends OpenTriviaDBAPIException {
    public OpenTriviaDBAPIInvalidParameterException() {
        super("Contains an invalid parameter. Arguments passed in aren't valid. (Ex. Amount = Five)");
    }

    public OpenTriviaDBAPIInvalidParameterException(String message) {
        super(message);
    }
}
