package ru.nsd.servlets;

import ru.nsd.DayPlan;
import ru.nsd.LifePlan;
import ru.nsd.Model;
import ru.nsd.Noda;
import ru.nsd.services.ModelService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerServlet extends HttpServlet {

    ModelService modelService = new ModelService();
    Model model;
    LifePlan lifePlan;

    public void init(ServletConfig servletConfig) throws ServletException{
        super.init(servletConfig);
        model = (Model)getServletConfig().getServletContext().getAttribute("model");
        lifePlan = (LifePlan) getServletConfig().getServletContext().getAttribute("lifePlan");;
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getServletPath();
        switch(action){
            case("/save"):
                modelService.insert(model);
            case("/get"):
                List<Map<String, String>> dayPlans =  modelService.select(lifePlan);
                if(dayPlans != null){
                    for(Map<String, String> dayPlan:dayPlans){
                        lifePlan.fillNonVisitNodes();
                        DayPlan dPl = new DayPlan(dayPlan);
                        lifePlan.fillPlanOfLeaves(dPl);
                        lifePlan.fillVisitNodesForPrinting();
                        lifePlan.printDayPlanToFile();
                    }

                }
        }
    }
}