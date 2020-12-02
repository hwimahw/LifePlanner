package ru.nsd.dao;
import ru.nsd.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelDao {
    public void create(List<Noda> leaves){ // Названия листьев в xml файле должны соответствовать тому, как мы хотим назвать колонки таблицы в БД
        String sql = "create table lifeplan ( ";
        for(Noda leaf:leaves){
            sql = sql + leaf.getName() + " varchar(60), ";
        }
        sql = changeLastCommaIntoGap(sql) + ")";
        try {
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        }catch(SQLException ex){
            ex.printStackTrace();
        }

    }

    public void insert(Model data){ // Названия листьев в xml файле должны соответствовать тому, как мы хотим назвать колонки таблицы в БД
        String sql1 = "insert into lifeplan ( ";
        String sql2 = "(";
        Map<String, String> dayPlan = data.getDayPlan();
        for(Map.Entry entry:dayPlan.entrySet()){
            sql1 = sql1 + entry.getKey() + ",";
            sql2 = sql2 + "?, ";
        }
        sql1 = changeLastCommaIntoGap(sql1) + ") VALUES ";
        sql2 = changeLastCommaIntoGap(sql2) + ")";
        String sql = sql1 + sql2;
        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            int i = 1;
            for(Map.Entry<String, String> entry:dayPlan.entrySet()){
                stmt.setString(i, entry.getValue());
                i++;
            }
            stmt.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public List<Map<String, String>> select(LifePlan lifePlan){ // Названия листьев в xml файле должны соответствовать тому, как мы хотим назвать колонки таблицы в БД
        List<Map<String, String>> dayPlans = null;
        String sql = "select * from lifeplan";
        try {
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            dayPlans = new ArrayList<>();
            while(resultSet.next()){
                Map<String, String> dayPlan = new HashMap<>();
                for(int i = 1; i <= lifePlan.getLeaves().size(); i++){
                    dayPlan.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));
                }
                dayPlans.add(dayPlan);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return dayPlans;
    }

    private String changeLastCommaIntoGap(String sql){
        int lastIndexOfComma = sql.lastIndexOf(',');
        char[] array = sql.toCharArray();
        array[lastIndexOfComma] = ' ';
        return String.valueOf(array);
    }


}
