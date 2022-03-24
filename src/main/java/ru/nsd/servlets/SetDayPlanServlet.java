package ru.nsd.servlets;

import ru.nsd.DayPlan;
import ru.nsd.LifePlan;
import ru.nsd.Noda;
import ru.nsd.utils.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.hasText;

public class SetDayPlanServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        setLeafPlan(request, response);
    }

    protected void setLeafPlan(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LifePlan lifePlan = (LifePlan) getServletContext().getAttribute("lifePlan");
        DayPlan dayPlan = buildDayPlan(request, lifePlan);
        lifePlan.fillPlanOfLeaves(dayPlan);
        request.setAttribute("dayPlan", dayPlan);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/save");
        requestDispatcher.forward(request, response);
    }

    private DayPlan buildDayPlan(HttpServletRequest request, LifePlan lifePlan) {
        List<Noda> leaves = lifePlan.getLeaves();
        Map<String, String> dayPlanMap = new HashMap<>();
        LocalDate date = Utils.buildDate(request.getParameter("date"));
        for (Noda leaf : leaves) {
            String subject = leaf.getName();
            String plan = request.getParameter(subject);
            if (hasText(plan)) {
                dayPlanMap.put(subject, plan);
            }
        }
        return new DayPlan(date, dayPlanMap);
    }
}