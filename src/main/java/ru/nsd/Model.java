package ru.nsd;

import java.util.HashMap;
import java.util.Map;

public class Model {
    Map<String, String> dayPlan;

    public Model(Map<String, String> dayPlan) {
        this.dayPlan = dayPlan;
    }

    public Map<String, String> getDayPlan() {
        return dayPlan;
    }

    public void setDayPlan(Map<String, String> dayPlan) {
        this.dayPlan = dayPlan;
    }
}
