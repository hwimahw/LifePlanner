package ru.nsd.servlets;

import ru.nsd.LifePlan;
import ru.nsd.models.LifeDirection;
import ru.nsd.models.User;
import ru.nsd.services.LifePlanCycleService;
import ru.nsd.services.lifeDirection.LifeDirectionService;
import ru.nsd.services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;

public class RegisterPageServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final LifePlanCycleService lifePlanCycleService = new LifePlanCycleService();
    private final LifeDirectionService lifeDirectionService = new LifeDirectionService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login != null && password != null) {
            if (userService.get(new User(login, password)) != null) {
                request.setAttribute("error", "User already exists");
                request.getRequestDispatcher("/registerPage").forward(request, response);
            } else {
                userService.add(new User(login, password));
                request.setAttribute("success", "Registration was successful");
                request.getRequestDispatcher("/logInPage").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (isNull(login) || isNull(password) || isNull(userId)) {
            request.getRequestDispatcher("registerPage.jsp").forward(request, response);
        } else {
            List<LifeDirection> lifeDirections = lifeDirectionService.get(userId);
            LifePlan lifePlan = lifePlanCycleService.prepareLifeDirectionsToLifePlan(lifeDirections);
            request.getSession().setAttribute("leaves", lifePlan.getLeaves());
            request.getRequestDispatcher("/lifePlanInput").forward(request, response);
        }
    }
}
