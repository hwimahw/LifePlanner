package ru.nsd.servlets;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.nsd.models.DayPlan;
import ru.nsd.LifePlan;
import ru.nsd.services.dayplan.DayPlanService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import static ru.nsd.utils.Utils.buildDate;

public class GetLifePlanServlet extends HttpServlet {

    @Autowired
    private DayPlanService dayPlanService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        createFile(request, response);
        downloadFile(response);
    }

    private void createFile(HttpServletRequest request, HttpServletResponse response) {
        LifePlan lifePlan = (LifePlan) request.getSession().getAttribute("lifePlan");
        Long userId = (Long) request.getSession().getAttribute("userId");
//        if (lifePlan == null) {
//            File file = new File("out.txt");
//            downloadFile(response);
//            return;
//        }
        List<Map<String, String>> dayPlans = dayPlanService.select(userId);
        for (Map<String, String> dayPlan : dayPlans) {
            lifePlan.fillNonVisitNodes();
            DayPlan dayPlanModel = new DayPlan(buildDate(dayPlan.get("DATE")), dayPlan);
            lifePlan.fillPlanOfLeaves(dayPlanModel);
            lifePlan.fillVisitNodesForPrinting();
            lifePlan.printDayPlanToFile(dayPlanModel);
        }
        downloadFile(response);
    }

    private void downloadFile(HttpServletResponse response) {
        String filePath = "out.txt";
        File downloadFile = new File(filePath);

        try (FileInputStream inStream = new FileInputStream(downloadFile);
             OutputStream outStream = response.getOutputStream()) {

            String mimeType = getServletContext().getMimeType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            byte[] byteArray = new byte[4096];
            int bytesRead;
            while ((bytesRead = inStream.read(byteArray)) != -1) {
                outStream.write(byteArray, 0, bytesRead);
            }
        } catch (IOException ex){
            throw new RuntimeException();
        }
    }
}