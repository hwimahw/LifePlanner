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
import java.util.List;

public class SaveDayPlanServlet extends HttpServlet {

    private final DayPlanService dayPlanService = new DayPlanService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DayPlan dayPlan = (DayPlan) request.getAttribute("dayPlan");
        dayPlanService.insert(dayPlan);
        List<String> leaves = (List<String>) request.getSession().getAttribute("leaves");
        request.setAttribute("leaves", leaves);
        request.getRequestDispatcher("/lifePlanInput").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }
}