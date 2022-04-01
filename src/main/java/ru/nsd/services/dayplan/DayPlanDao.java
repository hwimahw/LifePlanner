package ru.nsd.services.dayplan;

import ru.nsd.DayPlan;
import ru.nsd.exceptions.ConnectionWithDataBaseException;
import ru.nsd.services.hikaripool.HikariPoolService;

import java.sql.*;
import java.util.Map;

public class DayPlanDao {


    public void insert(DayPlan dayPlan) {
        for (Map.Entry<String, String> entry : dayPlan.getDayPlan().entrySet()) {
            String sql = "INSERT INTO LIFEPLAN VALUES (?, ?, ?, ?)";
            try {
                Connection conn = HikariPoolService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setObject(1, "1212");
                stmt.setObject(2, dayPlan.getDate());
                stmt.setString(3, entry.getKey());
                stmt.setString(4, entry.getValue());
                stmt.executeUpdate();
            } catch (SQLException exception) {
                throw new ConnectionWithDataBaseException();
            }
        }
    }
//
//    public List<Map<String, String>> select(LifePlan lifePlan){ // Названия листьев в xml файле должны соответствовать тому, как мы хотим назвать колонки таблицы в БД
//        List<Map<String, String>> dayPlans = null;
//        String sql = "select * from lifeplan";
//        try {
//            Connection conn = ConnectionFactory.getConnection();
//            Statement stmt = conn.createStatement();
//            ResultSet resultSet = stmt.executeQuery(sql);
//            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//            dayPlans = new ArrayList<>();
//            while(resultSet.next()){
//                Map<String, String> dayPlan = new HashMap<>();
//                for(int i = 1; i <= lifePlan.getLeaves().size(); i++){
//                    dayPlan.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));
//                }
//                dayPlans.add(dayPlan);
//            }
//        }catch(SQLException ex){
//            ex.printStackTrace();
//        }
//        return dayPlans;
//    }

}
