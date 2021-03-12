package ru.nsd.models;

import javax.validation.constraints.*;
import java.sql.Date;

public class Idea {

    private static int id = 0;
    private Date date;
    @Size(min = 1, message = "The idea is empty")
    private String idea;


    public Idea(Date date, String idea) {
        this.idea = idea;
        this.date = date;
        ++id;
    }

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
