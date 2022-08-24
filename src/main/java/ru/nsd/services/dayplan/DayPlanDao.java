package ru.nsd.services.dayplan;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nsd.models.DayPlan;

import java.util.*;

@Component
public class DayPlanDao {

    private final JdbcTemplate jdbcTemplate;

    public DayPlanDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(DayPlan dayPlan) {
        for (Map.Entry<String, String> entry : dayPlan.getDayPlan().entrySet()) {
            jdbcTemplate.update("INSERT INTO LIFEPLAN(USER_ID, DATE, SUBJECT, PLAN) VALUES (?, ?, ?, ?)",
                    dayPlan.getUserId(), dayPlan.getDate(), entry.getKey(), entry.getValue());
        }
    }

    public List<Map<String, String>> select(Long userId) { // Названия листьев в xml файле должны соответствовать тому, как мы хотим назвать колонки таблицы в БД
        return jdbcTemplate.query("select date, subject, plan from lifeplan where user_id = ? order by date", new DayPlansResultSetExtractor(), userId);
    }
}
