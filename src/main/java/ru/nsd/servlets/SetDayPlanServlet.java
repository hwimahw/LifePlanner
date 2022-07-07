package ru.nsd.servlets;

import ru.nsd.Noda;
import ru.nsd.models.DayPlan;
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
        setLeafPlan(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }

    protected void setLeafPlan(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Noda> leaves = (List) request.getSession().getAttribute("leaves");
        DayPlan dayPlan = buildDayPlan(request, leaves);
        request.setAttribute("dayPlan", dayPlan);
        request.getRequestDispatcher("/save").forward(request, response);
    }

    private DayPlan buildDayPlan(HttpServletRequest request, List<Noda> leaves) {
        Map<String, String> dayPlanMap = new HashMap<>();
        Long userId = (Long) request.getSession().getAttribute("userId");
        LocalDate date = Utils.buildDate(request.getParameter("date"));
        for (Noda leave : leaves) {
            String subject = leave.getName();
            String plan = request.getParameter(subject);
            if (hasText(plan)) {
                dayPlanMap.put(subject, plan);
            }
        }
        return new DayPlan(userId, date, dayPlanMap);
    }
}