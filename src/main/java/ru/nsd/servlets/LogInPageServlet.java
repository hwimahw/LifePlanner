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

public class LogInPageServlet extends HttpServlet {

    private final LifePlanCycleService lifePlanCycleService = new LifePlanCycleService();
    private final LifeDirectionService lifeDirectionService = new LifeDirectionService();
    private final UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (isNull(login) || isNull(password)) {
            request.setAttribute("error", "Such user doesn't exist");
            request.getRequestDispatcher("logInPage.jsp").forward(request, response);
            return;
        }
        User user = userService.get(new User(login, password));
        if (isNull(user)) {
            request.setAttribute("error", "Such user doesn't exist");
            request.getRequestDispatcher("logInPage.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("login", user.getLogin());
            request.getSession().setAttribute("password", user.getPassword());
            request.getSession().setAttribute("userId", user.getId());
            List<LifeDirection> lifeDirections = lifeDirectionService.get(user.getId());
            LifePlan lifePlan = lifePlanCycleService.prepareLifeDirectionsToLifePlan(lifeDirections);
            request.getSession().setAttribute("leaves", lifePlan.getLeaves());
            request.setAttribute("lifePlan", lifePlan);
            request.getRequestDispatcher("/lifePlanInput").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = (String) request.getSession().getAttribute("login");
        String password = (String) request.getSession().getAttribute("password");
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (isNull(login) || isNull(password) || isNull(userId)) {
            request.getRequestDispatcher("logInPage.jsp").forward(request, response);
        } else {
            List<LifeDirection> lifeDirections = lifeDirectionService.get(userId);
            LifePlan lifePlan = lifePlanCycleService.prepareLifeDirectionsToLifePlan(lifeDirections);
            request.getSession().setAttribute("leaves", lifePlan.getLeaves());
            response.sendRedirect("/lifePlanInput");
            request.getRequestDispatcher("/lifePlanInput").forward(request, response);
        }
    }
}
