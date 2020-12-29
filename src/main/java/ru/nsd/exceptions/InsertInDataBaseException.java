package ru.nsd.exceptions;

public class InsertInDataBaseException extends RuntimeException {
    public InsertInDataBaseException(String message){
        super(message);
    }
}
