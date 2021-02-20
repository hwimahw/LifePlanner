package ru.nsd.models;


import java.sql.Date;

public class Idea {

    public Idea(Date date, String idea){
        this.idea = idea;
        this.date = date;
    }

    private String idea;

    private Date date;

    public String getIdea() {
        return idea;
    }

    public Date getDate() {
        return date;
    }
}
