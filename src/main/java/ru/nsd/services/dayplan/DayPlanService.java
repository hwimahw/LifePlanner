package ru.nsd.services.dayplan;

import ru.nsd.DayPlan;

public class DayPlanService {

    DayPlanDao dayPlanDao = new DayPlanDao();

    public void insert(DayPlan dayPlan) {
        dayPlanDao.insert(dayPlan);
    }
//
//    public List<Map<String, String>> select(LifePlan lifePlan){
//        return dayPlanDao.select(lifePlan);
//    }
}
