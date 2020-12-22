package ru.nsd.servlets;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.ServletContextAware;
import ru.nsd.DayPlan;
import ru.nsd.LifePlan;
import ru.nsd.Model;
import ru.nsd.services.ModelService;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerServlet extends HttpServlet implements ServletContextAware {

    ModelService modelService = new ModelService();
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
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("lifePlanInput.jsp");
                requestDispatcher.forward(request, response);
                break;
            case ("get"):
                lifePlan = (LifePlan) getServletContext().getAttribute("lifePlan");
                if(lifePlan == null){
                    return; /// EXCEPTION
                }
                List<Map<String, String>> dayPlans = modelService.select(lifePlan);
                if (dayPlans != null) {
                    for (Map<String, String> dayPlan : dayPlans) {
                        lifePlan.fillNonVisitNodes();
                        DayPlan dPl = new DayPlan(dayPlan);
                        lifePlan.fillPlanOfLeaves(dPl);
                        lifePlan.fillVisitNodesForPrinting();
                        lifePlan.printDayPlanToFile(dayPlan.get("DATE"));
                    }
                }
                String filePath = "out.txt";
                File downloadFile = new File(filePath);
                downloadFile.createNewFile();
                FileInputStream inStream = new FileInputStream(downloadFile);
                ServletContext context = getServletContext();
                String mimeType = context.getMimeType(filePath);
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }
                System.out.println("MIME type: " + mimeType);
                response.setContentType(mimeType);
                response.setContentLength((int)downloadFile.length());

                // forces download
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
                response.setHeader(headerKey, headerValue);

                OutputStream outStream = response.getOutputStream();
                byte[] byteArray = new byte[4096];
                int bytesRead = -1;
                //byte[] byteArray = FileUtils.readFileToByteArray(new File("out.txt"));
                while ((bytesRead = inStream.read(byteArray)) != -1) {
                    outStream.write(byteArray, 0, bytesRead);
                }
                inStream.close();
                outStream.close();
        }
    }
    @Override
    public void setServletContext(ServletContext servletContext){
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext(){
        return servletContext;
    }
}