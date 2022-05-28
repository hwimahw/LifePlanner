package ru.nsd.services.dayplan;

import ru.nsd.models.DayPlan;

import java.util.List;
import java.util.Map;

public class DayPlanService {

    DayPlanDao dayPlanDao = new DayPlanDao();

    public void insert(DayPlan dayPlan) {
        dayPlanDao.insert(dayPlan);
    }

    public List<Map<String, String>> select(){
        return dayPlanDao.select();
    }
}
