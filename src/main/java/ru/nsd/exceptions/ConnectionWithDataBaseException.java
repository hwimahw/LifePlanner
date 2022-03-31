package ru.nsd.exceptions;

public class ConnectionWithDataBaseException extends RuntimeException {
    public ConnectionWithDataBaseException(String message) {
        super(message);
    }

    public ConnectionWithDataBaseException() {
    }
}
