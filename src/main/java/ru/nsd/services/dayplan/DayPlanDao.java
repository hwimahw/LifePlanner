package ru.nsd.services.dayplan;

import ru.nsd.models.DayPlan;
import ru.nsd.exceptions.ConnectionWithDataBaseException;
import ru.nsd.services.hikaripool.HikariPoolService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayPlanDao {

    public void insert(DayPlan dayPlan) {
        for (Map.Entry<String, String> entry : dayPlan.getDayPlan().entrySet()) {
            String sql = "INSERT INTO LIFEPLAN(USER_ID, DATE, SUBJECT, PLAN) VALUES (?, ?, ?, ?)";
            try {
                Connection conn = HikariPoolService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setObject(1, dayPlan.getUserId());
                stmt.setObject(2, dayPlan.getDate());
                stmt.setString(3, entry.getKey());
                stmt.setString(4, entry.getValue());
                stmt.executeUpdate();
            } catch (SQLException exception) {
                throw new ConnectionWithDataBaseException();
            }
        }
    }

    public List<Map<String, String>> select() { // Названия листьев в xml файле должны соответствовать тому, как мы хотим назвать колонки таблицы в БД
        String sql = "select date, subject, plan from lifeplan order by date";
        try {
            Connection conn = HikariPoolService.getConnection();
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stmt.executeQuery(sql);
            return getDayPlans(resultSet);
        } catch (SQLException ex) {
            throw new ConnectionWithDataBaseException();
        }
    }

    private List<Map<String, String>> getDayPlans(ResultSet resultSet) {
        List<Map<String, String>> dayPlans = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String date = resultSet.getString(1);
                Map<String, String> dayPlan = new HashMap<>();
                while (resultSet.getString(1).equals(date)) {
                    dayPlan.put("DATE", date);
                    dayPlan.put(resultSet.getString(2), resultSet.getString(3));
                    if (!resultSet.next()) {
                        break;
                    }
                }
                dayPlans.add(dayPlan);
                resultSet.previous();
            }
        } catch (SQLException ex) {
            throw new ConnectionWithDataBaseException();
        }
        return dayPlans;
    }
}
