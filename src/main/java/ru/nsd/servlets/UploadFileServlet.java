package ru.nsd.servlets;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import ru.nsd.LifePlan;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@MultipartConfig
@Component("upload")
public class UploadFileServlet extends HttpServlet implements ServletContextAware, ServletConfigAware {

    private LifePlan lifePlan;
    ServletConfig servletConfig;
    ServletContext servletContext;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        MultipartFile file = (MultipartFile)request.getAttribute("file");
        InputStream fileContent = file.getInputStream();
        lifePlan = new LifePlan(fileContent);
        getServletContext().setAttribute("lifePlan", lifePlan);
        request.setAttribute("leaves", lifePlan.getLeaves());
        request.setAttribute("aaa", lifePlan.getObject());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("lifePlanInput.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    public void setServletConfig(ServletConfig servletConfig){
        this.servletConfig = servletConfig;
    }

    @Override
    public void setServletContext(ServletContext servletContext){
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext(){
        return servletContext;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }

}