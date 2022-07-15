package ru.nsd.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.nsd.models.DayPlan;
import ru.nsd.LifePlan;
import ru.nsd.services.dayplan.DayPlanService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SaveDayPlanServlet extends HttpServlet {

    @Autowired
    private DayPlanService dayPlanService;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

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