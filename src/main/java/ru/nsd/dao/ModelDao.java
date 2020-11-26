package ru.nsd.dao;
import ru.nsd.Model;
import ru.nsd.ConnectionFactory;
import java.sql.*;
import java.util.Map;

public class ModelDao implements Dao<Model> {
    public void create(Model data){ // Названия листьев в xml файле должны соответствовать тому, как мы хотим назвать колонки таблицы в БД
        String sql = "create table lifeplan ( ";
        Map<String, String> dayPlan = data.getDayPlan();
        for(Map.Entry entry:dayPlan.entrySet()){
            sql = sql + entry.getKey() + " varchar(60), ";
        }
        sql = changeLastCommaIntoGap(sql) + ")";
    }

    public boolean insert(Model data){ // Названия листьев в xml файле должны соответствовать тому, как мы хотим назвать колонки таблицы в БД
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
    }

    private String changeLastCommaIntoGap(String sql){
        int lastIndexOfComma = sql.lastIndexOf(',');
        char[] array = sql.toCharArray();
        array[lastIndexOfComma] = ' ';
        return String.valueOf(array);
    }


}
