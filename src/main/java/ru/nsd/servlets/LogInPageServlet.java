package ru.nsd.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.nsd.LifePlan;
import ru.nsd.models.LifeDirection;
import ru.nsd.models.User;
import ru.nsd.services.LifePlanCycleService;
import ru.nsd.services.lifeDirection.LifeDirectionService;
import ru.nsd.services.user.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;

public class LogInPageServlet extends HttpServlet {

    @Autowired
    private LifePlanCycleService lifePlanCycleService;
    @Autowired
    private LifeDirectionService lifeDirectionService;
    @Autowired
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (!hasText(login) || !hasText(password)) {
            request.setAttribute("error", "Such user doesn't exist");
            request.getRequestDispatcher("/logInPage").forward(request, response);
            return;
        }
        User user = userService.get(new User(login, password));
        if (isNull(user)) {
            request.setAttribute("error", "Such user doesn't exist");
            request.getRequestDispatcher("/logInPage").forward(request, response);
        } else {
            request.getSession().setAttribute("login", user.getLogin());
            request.getSession().setAttribute("password", user.getPassword());
            request.getSession().setAttribute("userId", user.getId());
            List<LifeDirection> lifeDirections = lifeDirectionService.get(user.getId());
            LifePlan lifePlan = lifePlanCycleService.prepareLifeDirectionsToLifePlan(lifeDirections);
            request.getSession().setAttribute("lifePlan", lifePlan);
            request.getSession().setAttribute("leaves", lifePlan.getLeaves());
            response.sendRedirect("/lifePlanInput");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/logInPage");
    }


}
