package ru.nsd.models;

import java.time.LocalDate;
import java.util.Map;

public class DayPlan {

    private final LocalDate date;
    private final Map<String, String> dayPlan;

    public DayPlan(LocalDate date, Map<String, String> dayPlan) {
        this.date = date;
        this.dayPlan = dayPlan;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<String, String> getDayPlan() {
        return dayPlan;
    }
}
