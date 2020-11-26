package ru.nsd;

import java.util.HashMap;
import java.util.Map;

public class Model {
    Map<String, String> dayPlan = new HashMap<>();

    public Map<String, String> getDayPlan() {
        return dayPlan;
    }

    public void setDayPlan(Map<String, String> dayPlan) {
        this.dayPlan = dayPlan;
    }
}
