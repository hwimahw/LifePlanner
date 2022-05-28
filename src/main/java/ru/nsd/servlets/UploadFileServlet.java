package ru.nsd.servlets;

import ru.nsd.LifePlan;
import ru.nsd.Noda;
import ru.nsd.exceptions.ConnectionWithDataBaseException;
import ru.nsd.models.LifeDirection;
import ru.nsd.services.LifePlanCycleService;
import ru.nsd.services.hikaripool.HikariPoolService;
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

        getServletContext().setAttribute("lifePlan", lifePlan);
        request.setAttribute("leaves", lifePlan.getLeaves());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("lifePlanInput.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }

    private void addLifePlanToDataBase(LifePlan lifePlan) {
        goThroughLifePlan(lifePlan.getRoot(), 1);
    }

    private void goThroughLifePlan(Noda noda, int position) {
        if (noda == null) {
            return;
        }
        insert(noda.getName(), position);
        for (Noda child : noda.getChildren()) {
            goThroughLifePlan(child, position + 1);
        }
    }

    private void insert(String name, int position){
//        Connection conn = HikariPoolService.getConnection();
//        String sql = "insert into subjects values (?, ?, ?)";
//        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//        ResultSet resultSet = stmt.executeQuery(sql);
//        Connection conn = HikariPoolService.getConnection();
//        PreparedStatement stmt = conn.prepareStatement(sql);
//        stmt.setObject(1, i);
//        stmt.setObject(2, "2323");
//        stmt.setObject(3, dayPlan.getDate());
//        stmt.setString(4, entry.getKey());
//        stmt.setString(5, entry.getValue());
//        stmt.executeUpdate();
    }
}