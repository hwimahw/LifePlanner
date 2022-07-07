package ru.nsd.utils;

import ru.nsd.exceptions.DateBuildException;

import java.time.LocalDate;

public class Utils {

    public static LocalDate buildDate(String date) {
        String[] dateElements = date.split("-");
        try {
            String year = dateElements[0];
            String month = dateElements[1];
            String dayOfMonth = dateElements[2];
            return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(dayOfMonth));
        } catch (Exception exception) {
            throw new DateBuildException();
        }
    }
}
