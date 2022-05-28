package ru.nsd.models;

import java.time.LocalDate;
import java.util.Map;

public class DayPlan {

    private LocalDate date;
    private Map<String, String> dayPlan;

    public DayPlan(LocalDate date, Map<String, String> dayPlan) {
        this.date = date;
        this.dayPlan = dayPlan;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, String> getDayPlan() {
        return dayPlan;
    }

    public void setDayPlan(Map<String, String> dayPlan) {
        this.dayPlan = dayPlan;
    }
}
