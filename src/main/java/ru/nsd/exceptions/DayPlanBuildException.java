package ru.nsd.exceptions;

public class DayPlanBuildException extends RuntimeException {

    public DayPlanBuildException() {
    }

    public DayPlanBuildException(String message) {
        super(message);
    }
}
