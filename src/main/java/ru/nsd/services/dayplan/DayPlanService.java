package ru.nsd.services.dayplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsd.models.DayPlan;

import java.util.List;
import java.util.Map;

@Component
public class DayPlanService {

    private final DayPlanDao dayPlanDao;

    @Autowired
    public DayPlanService(DayPlanDao dayPlanDao) {
        this.dayPlanDao = dayPlanDao;
    }

    public void insert(DayPlan dayPlan) {
        dayPlanDao.insert(dayPlan);
    }

    public List<Map<String, String>> select() {
        return dayPlanDao.select();
    }
}
