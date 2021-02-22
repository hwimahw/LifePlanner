package ru.nsd.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import ru.nsd.DayPlan;
import ru.nsd.LifePlan;
import ru.nsd.Model;
import ru.nsd.exceptions.NoLifePlanException;
import ru.nsd.services.ModelService;
import ru.nsd.utils.FileUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@Component("controller")
public class ControllerServlet extends HttpServlet implements ServletContextAware {

    @Autowired
    ModelService modelService;
    ServletContext servletContext;
    Model model;
    LifePlan lifePlan;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("name");
        switch (action) {
            case ("save"):
                model = (Model) getServletContext().getAttribute("model");
                modelService.insert(model);
                lifePlan = (LifePlan) getServletContext().getAttribute("lifePlan");
                request.setAttribute("leaves", lifePlan.getLeaves());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/lifePlan/lifePlanInput.jsp");
                requestDispatcher.forward(request, response);
                break;
            case ("get"):
                lifePlan = (LifePlan) getServletContext().getAttribute("lifePlan");
                if (lifePlan == null) {
                    throw new NoLifePlanException("LifePlan is not defined");
                }
                List<Map<String, String>> dayPlans = modelService.select(lifePlan);
                if (dayPlans != null) {
                    for (Map<String, String> dayPlan : dayPlans) {
                        lifePlan.fillNonVisitNodes();
                        DayPlan dPl = new DayPlan(dayPlan);
                        lifePlan.addDayPlan(dPl, dayPlan.get("DATE"));
                    }
                }
                FileUtils.sendFile(request, response, "out.txt");
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}