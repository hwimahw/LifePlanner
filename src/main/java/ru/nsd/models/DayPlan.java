package ru.nsd.models;

import java.time.LocalDate;
import java.util.Map;

public class DayPlan {

    private final Long userId;
    private final LocalDate date;
    private final Map<String, String> dayPlan;

    public DayPlan(Long userId, LocalDate date, Map<String, String> dayPlan) {
        this.userId = userId;
        this.date = date;
        this.dayPlan = dayPlan;
    }

    public DayPlan(LocalDate date, Map<String, String> dayPlan) {
        this.date = date;
        this.dayPlan = dayPlan;
        userId = null;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<String, String> getDayPlan() {
        return dayPlan;
    }

    public Long getUserId() {
        return userId;
    }
}
