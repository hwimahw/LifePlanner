package ru.nsd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nsd.exceptions.WorkWithDataBaseException;
import ru.nsd.exceptions.WriteToFileException;
import ru.nsd.models.Idea;
import ru.nsd.utils.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Component
public class IdeaDao {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void getAllIdeas(HttpServletRequest request, HttpServletResponse response) {

        try {
            List<Idea> ideas = jdbcTemplate.query("select * from TABLE_IDEAS", new BeanPropertyRowMapper<>(Idea.class));
            printIdeasToFile(ideas, "AllIdeas.txt");
            FileUtils.sendFile(request, response, "AllIdeas.txt");
        } catch (DataAccessException ex) {
            WorkWithDataBaseException e = new WorkWithDataBaseException("get all ideas exception");
            e.initCause(ex);
            throw e;
        }
    }

    public void getIdeasByDate(HttpServletRequest request, HttpServletResponse response,
                               String date) {
        try {
            List<Idea> ideas = jdbcTemplate.query("select * from TABLE_IDEAS where date = ?", new Object[]{date}, new BeanPropertyRowMapper<>(Idea.class));
            printIdeasToFile(ideas, "AllIdeas.txt");
            FileUtils.sendFile(request, response, "AllIdeas.txt");
        } catch (DataAccessException ex) {
            WorkWithDataBaseException e = new WorkWithDataBaseException("get ideas by date exception");
            e.initCause(ex);
            throw e;
        }
    }

    public void setIdea(Idea idea) {

        try {
            jdbcTemplate.update("insert into TABLE_IDEAS values (?, ?, ?)", idea.getId(), idea.getDate(), idea.getIdea());
        } catch (DataAccessException ex) {
            WorkWithDataBaseException e = new WorkWithDataBaseException("set idea exception");
            e.initCause(ex);
            throw e;
        }
    }

    public void editIdea(int id, String idea) {
        try {
            jdbcTemplate.update("update TABLE_IDEAS set idea = ? where id = ?", idea, id);
        } catch (DataAccessException ex) {
            WorkWithDataBaseException e = new WorkWithDataBaseException("edit idea exception");
            e.initCause(ex);
            throw e;
        }
    }

    public void deleteIdea(int id) {
        try {
            jdbcTemplate.update("delete from TABLE_IDEAS where id = ?", id);
        } catch (DataAccessException ex) {
            WorkWithDataBaseException e = new WorkWithDataBaseException("delete idea exception");
            e.initCause(ex);
            throw e;
        }
    }


    private void printIdeasToFile(List<Idea> ideas, String nameOfFile) {
        File file = new File(nameOfFile);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file, false);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (Idea idea : ideas) {
                bufferedWriter.write("-----------------------------------------------------------\n");
                bufferedWriter.write(idea.getDate().toString() + "\n\n");
                bufferedWriter.write(idea.getIdea() + "\n\n");
            }
        } catch (IOException e) {
            throw new WriteToFileException("Writing to file exception");
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
