package com.triviagame.triviagame.controller.exception;

public class InvalidInputException extends Exception {
    public InvalidInputException() {
        super("Unknown reason input exception");
    }

    public InvalidInputException(String message) {
        super(message);
    }
}
