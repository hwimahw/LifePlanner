package ru.nsd.servlets;

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

public class LogInPageServlet extends HttpServlet {

    LifePlanCycleService lifePlanCycleService = new LifePlanCycleService();
    LifeDirectionService lifeDirectionService = new LifeDirectionService();
    UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User user = userService.get(new User(login, password));
        if (login != null && password != null && user != null) {
            request.getSession().setAttribute("login", user.getLogin());
            request.getSession().setAttribute("password", user.getPassword());
            request.getSession().setAttribute("userId", user.getId());
            List<LifeDirection> lifeDirections = lifeDirectionService.get(user.getId());
            request.getSession().setAttribute("leaves", lifePlanCycleService.prepareLifeDirectionsToLifePlanLeaves(lifeDirections));
            response.sendRedirect("/lifePlanInput");
        } else {
            request.setAttribute("error", "Такого пользователя не существует");
            response.sendRedirect("/logInPage");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("logInPage.jsp").forward(request, response);
    }
}