package ru.nsd.dao;

import org.springframework.stereotype.Component;
import ru.nsd.ConnectionFactory;
import ru.nsd.Noda;
import ru.nsd.models.Idea;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class IdeaDao {


    public void getIdeas(HttpServletRequest request, HttpServletResponse response) {
      //  try {
//            Connection connection = ConnectionFactory.getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select * from TABLE_IDEAS");
//            List<Idea> ideas = new ArrayList<>();
//            while(resultSet.next()){
//                Idea idea = new Idea(resultSet.getDate(1), resultSet.getString(2));
//                ideas.add(idea);
//            }
//            printIdeasToFile(ideas);
            sendFile(request, response);

//        } catch (SQLException ex) {
//
//        }
    }

    private void sendFile(HttpServletRequest request, HttpServletResponse response){
        try {
            String filePath = "ideas.txt";
            File downloadFile = new File(filePath);
            downloadFile.createNewFile();
            FileInputStream inStream = new FileInputStream(downloadFile);
            ServletContext context = request.getServletContext();
            String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            System.out.println("MIME type: " + mimeType);
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            OutputStream outStream = response.getOutputStream();
            byte[] byteArray = new byte[4096];
            int bytesRead = -1;
            //byte[] byteArray = FileUtils.readFileToByteArray(new File("out.txt"));
            while ((bytesRead = inStream.read(byteArray)) != -1) {
                outStream.write(byteArray, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
        }catch (Exception ex){

        }
    }

    private void printIdeasToFile(List<Idea> ideas) {
        File file = new File("ideas.txt");
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (Idea idea : ideas) {
                bufferedWriter.write("-----------------------------------------------------------\n");
                bufferedWriter.write(idea.getDate().toString() + "\n\n");
                bufferedWriter.write(idea.getIdea() + "\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
