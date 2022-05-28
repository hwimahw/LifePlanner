package ru.nsd.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommonFilter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String requestURI = ((HttpServletRequest) request).getRequestURI();

        if("/registerPage".equals(requestURI) || "/register".equals(requestURI)
            || "/logInPage".equals(requestURI) || "/logIn".equals(requestURI)){
            chain.doFilter(request, response);
            return;
        }
        if (session != null && session.getAttribute("login") != null
                && session.getAttribute("password") != null) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect("/logInPage");
        }
    }

    @Override
    public void destroy() {

    }
}
