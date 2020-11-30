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

@MultipartConfig
public class ControllerServlet extends HttpServlet {

    ModelService modelService;
    Model model;

    public void init(ServletConfig servletConfig) throws ServletException{
        super.init(servletConfig);
        modelService = new ModelService();
        model = (Model)getServletConfig().getServletContext().getAttribute("model");

    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getServletPath();
        switch(action){
            case("/save"):
                modelService.insert(model);
            case("/second"):
                //calling service method
        }
    }
}