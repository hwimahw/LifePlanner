package ru.nsd.exceptions;

public class WriteToFileException extends RuntimeException {

    public WriteToFileException(String msg){
        super(msg);
    }
}
