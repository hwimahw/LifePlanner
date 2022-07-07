package ru.nsd.servlets;

import ru.nsd.LifePlan;
import ru.nsd.models.LifeDirection;
import ru.nsd.services.LifePlanCycleService;
import ru.nsd.services.lifeDirection.LifeDirectionService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@MultipartConfig
public class UploadFileServlet extends HttpServlet {

    private LifePlanCycleService lifePlanCycleService = new LifePlanCycleService();
    private LifeDirectionService lifeDirectionService = new LifeDirectionService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        InputStream fileContent = filePart.getInputStream();
        LifePlan lifePlan = lifePlanCycleService.createLifePlan(fileContent);
        List<LifeDirection> lifeDirections = lifePlanCycleService.prepareLifePlanToLifeDirections(lifePlan, request);
        lifeDirectionService.add(lifeDirections);
        request.setAttribute("leaves", lifePlan.getLeaves());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/lifePlanInput");
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }
}