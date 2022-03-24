package ru.nsd.servlets;

import ru.nsd.DayPlan;
import ru.nsd.LifePlan;
import ru.nsd.Model;
import ru.nsd.services.ModelService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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

public class GetLifePlanServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        LifePlan lifePlan = (LifePlan) getServletContext().getAttribute("lifePlan");
//        if (lifePlan == null) {
//            return; /// EXCEPTION
//        }
//        List<Map<String, String>> dayPlans = modelService.select(lifePlan);
//        if (dayPlans != null) {
//            for (Map<String, String> dayPlan : dayPlans) {
//                lifePlan.fillNonVisitNodes();
//                DayPlan dPl = new DayPlan(dayPlan);
//                lifePlan.fillPlanOfLeaves(dPl);
//                lifePlan.fillVisitNodesForPrinting();
                lifePlan.printDayPlanToFile(dayPlan.get("DATE"));
//            }
//        }
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
        response.setContentLength((int) downloadFile.length());

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