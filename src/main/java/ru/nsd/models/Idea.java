package ru.nsd.models;


import java.sql.Date;

public class Idea {

    private static int id = 0;

    public Idea(Date date, String idea){
        this.idea = idea;
        this.date = date;
        ++id;
    }

    private String idea;

    private Date date;

    public String getIdea() {
        return idea;
    }

    public Date getDate() {
        return date;
    }

    public static int getId() {
        return id;
    }
}
