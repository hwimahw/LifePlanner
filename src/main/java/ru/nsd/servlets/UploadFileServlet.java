package ru.nsd.servlets;

import ru.nsd.LifePlan;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@MultipartConfig
public class UploadFileServlet extends HttpServlet {

    private LifePlan lifePlan;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        InputStream fileContent = filePart.getInputStream();
        lifePlan = new LifePlan(fileContent);
        getServletContext().setAttribute("lifePlan", lifePlan);
        request.setAttribute("leaves", lifePlan.getLeaves());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("lifePlanInput.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }
}