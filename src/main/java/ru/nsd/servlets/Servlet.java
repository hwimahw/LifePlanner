package ru.nsd.servlets;

import ru.nsd.LifePlan;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Servlet extends HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LifePlan lifePlan = new LifePlan();
        request.setAttribute("leaves", lifePlan.getLeaves());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("lifePlanInput.jsp");
        requestDispatcher.forward(request, response);
    }
}