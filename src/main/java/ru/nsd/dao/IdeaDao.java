package ru.nsd.dao;

import org.springframework.stereotype.Component;
import ru.nsd.ConnectionFactory;
import ru.nsd.Noda;
import ru.nsd.exceptions.WorkWithDataBaseException;
import ru.nsd.exceptions.WriteToFileException;
import ru.nsd.models.Idea;
import ru.nsd.utils.FileUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class IdeaDao {


    public void getAllIdeas(HttpServletRequest request, HttpServletResponse response) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from TABLE_IDEAS");
            List<Idea> ideas = new ArrayList<>();
            while(resultSet.next()){
                Idea idea = new Idea(resultSet.getDate(2), resultSet.getString(3));
                ideas.add(idea);
            }
            printIdeasToFile(ideas, "AllIdeas.txt");
            FileUtils.sendFile(request, response, "AllIdeas.txt");

        } catch (SQLException ex) {
           WorkWithDataBaseException e = new WorkWithDataBaseException("get all ideas exception");
           e.initCause(ex);
           throw e;
        }
    }

    public void getIdeasByDate(HttpServletRequest request, HttpServletResponse response,
                        String date) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from TABLE_IDEAS where date = ?");
            statement.setString(1, date);
            ResultSet resultSet = statement.executeQuery();
            List<Idea> ideas = new ArrayList<>();
            while(resultSet.next()){
                Idea idea = new Idea(resultSet.getDate(2), resultSet.getString(3));
                ideas.add(idea);
            }
            printIdeasToFile(ideas, "IdeasByDate.txt");
            FileUtils.sendFile(request, response, "IdeasByDate.txt");
        } catch (SQLException ex) {
            WorkWithDataBaseException e = new WorkWithDataBaseException("get ideas by date exception");
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

    public void setIdea(Idea idea){
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into TABLE_IDEAS values (?, ?, ?)");
            statement.setInt(1, idea.getId());
            statement.setDate(2, idea.getDate());
            statement.setString(3, idea.getIdea());
            statement.executeUpdate();
        }catch (SQLException ex){
            WorkWithDataBaseException e = new WorkWithDataBaseException("set idea exception");
            e.initCause(ex);
            throw e;

        }

    }

    public void editIdea(int id, String idea){
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("update TABLE_IDEAS set idea = ? where id = ?");
            statement.setString (1, idea);
            statement.setInt(2, id);
            statement.executeUpdate();
        }catch (SQLException ex){
            WorkWithDataBaseException e = new WorkWithDataBaseException("edit idea exception");
            e.initCause(ex);
            throw e;
        }

    }
}
