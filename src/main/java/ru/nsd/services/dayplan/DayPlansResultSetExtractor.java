package ru.nsd.services.dayplan;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.nsd.exceptions.ConnectionWithDataBaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DayPlansResultSetExtractor implements ResultSetExtractor<List<Map<String, String>>> {

    @Override
    public List<Map<String, String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        return getDayPlans(rs);
    }

    private List<Map<String, String>> getDayPlans(ResultSet resultSet) {
        List<Map<String, String>> dayPlans = new ArrayList<>();
        try {
//            resultSet.setFetchDirection(ResultSet.FETCH_UNKNOWN);
            Map<String, String> dayPlan = new HashMap<>();
            String date = null;
            if(resultSet.next()) {
                date = resultSet.getString(1);
                dayPlan.put("DATE", date);
                dayPlan.put(resultSet.getString(2), resultSet.getString(3));
                dayPlans.add(dayPlan);
            }
            while (resultSet.next()) {
                String dateForCompare = resultSet.getString(1);
                if (!dateForCompare.equals(date)) {
                    date = dateForCompare;
                    dayPlan = new HashMap<>();
                    dayPlan.put("DATE", date);
                } else {
                    dayPlans.remove(dayPlan);
                }
                dayPlans.add(addPlanToSubject(dayPlan, resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (SQLException ex) {
            throw new ConnectionWithDataBaseException();
        }
        return new ArrayList<>(dayPlans);
    }

    private Map<String, String> addPlanToSubject(Map<String, String> dayPlan, String subjectName, String plan){
        String definedPlan = dayPlan.get(subjectName);
        if (definedPlan != null) {
            definedPlan = definedPlan + ", " + plan;
            dayPlan.put(subjectName, definedPlan);
        } else {
            dayPlan.put(subjectName, plan);
        }
        return dayPlan;
    }
}
