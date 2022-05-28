package ru.nsd.exceptions;

public class UserBuildException extends RuntimeException {

    public UserBuildException() {
    }

    public UserBuildException(String message) {
        super(message);
    }
}
