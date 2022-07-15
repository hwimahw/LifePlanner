package ru.nsd.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.nsd.models.User;
import ru.nsd.services.user.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterPageServlet extends HttpServlet {

    @Autowired
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/registerPage");
    }
}
