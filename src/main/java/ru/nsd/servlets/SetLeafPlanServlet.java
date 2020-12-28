package ru.nsd.servlets;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import ru.nsd.DayPlan;
import ru.nsd.LifePlan;
import ru.nsd.Model;
import ru.nsd.Noda;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("setLeafPlan")
public class SetLeafPlanServlet extends HttpServlet implements ServletContextAware {

    ServletContext servletContext;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        setLeafPlan(request, response);
    }

    @Override
    public void setServletContext(ServletContext servletContext){
        this.servletContext = servletContext;

    }

    public ServletContext getServletContext(){
        return servletContext;
    }

    protected void setLeafPlan(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        LifePlan lifePlan = (LifePlan)getServletContext().getAttribute("lifePlan");
        List<Noda> leaves = lifePlan.getLeaves();
        Map<String, String> dayPlanMap = new HashMap<>();
        String date = request.getParameter("Date");
        dayPlanMap.put("Date", date);
        for (Noda leaf : leaves) {
            String subject = leaf.getName();
            String plan = request.getParameter(subject);
            if (!("".equals(plan))) {
                dayPlanMap.put(subject, plan);
            }
        }
        DayPlan dayPlan = new DayPlan(dayPlanMap);
        lifePlan.fillPlanOfLeaves(dayPlan);
        Model model = new Model(dayPlanMap);
        getServletContext().setAttribute("model", model);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Ctrl?name=save");
        requestDispatcher.forward(request, response);
    }
}