package ru.nsd.servlets;

import ru.nsd.models.DayPlan;
import ru.nsd.LifePlan;
import ru.nsd.services.dayplan.DayPlanService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveDayPlanServlet extends HttpServlet {

    private final DayPlanService dayPlanService = new DayPlanService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DayPlan dayPlan = (DayPlan) request.getAttribute("dayPlan");
        dayPlanService.insert(dayPlan);
        LifePlan lifePlan = (LifePlan) getServletContext().getAttribute("lifePlan");
        request.setAttribute("leaves", lifePlan.getLeaves());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("lifePlanInput.jsp");
        requestDispatcher.forward(request, response);
    }
}