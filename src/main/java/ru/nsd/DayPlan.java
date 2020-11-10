package ru.nsd;

import java.util.HashMap;
import java.util.Map;

public class DayPlan {
    private Map<String, String> dayPlan;

    public DayPlan(Map<String, String> dayPlan){
        this.dayPlan = dayPlan;
    }

    public Map<String, String> getDayPlan() {
        return dayPlan;
    }
}
