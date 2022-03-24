package ru.nsd.servlets;

import ru.nsd.DayPlan;
import ru.nsd.LifePlan;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveDayPlanServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DayPlan dayPlan = (DayPlan) request.getAttribute("dayPlan");
//        modelService.insert(dayPlan);
        LifePlan lifePlan = (LifePlan) getServletContext().getAttribute("lifePlan");
        request.setAttribute("leaves", lifePlan.getLeaves());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("lifePlanInput.jsp");
        requestDispatcher.forward(request, response);
    }
}