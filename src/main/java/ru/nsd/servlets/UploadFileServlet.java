package ru.nsd.servlets;


import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import ru.nsd.LifePlan;
import ru.nsd.exceptions.InvalidXmlFileException;
import ru.nsd.exceptions.NoFileLifePlanException;
import ru.nsd.validation.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@MultipartConfig
@Component("upload")
public class UploadFileServlet extends HttpServlet implements ServletContextAware, ServletConfigAware {

    private LifePlan lifePlan;
    ServletConfig servletConfig;
    ServletContext servletContext;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException, InvalidXmlFileException {
        MultipartFile file = (MultipartFile) request.getAttribute("file");
        InputStream fileContent;
        if (file == null) {
            throw new NoFileLifePlanException("No file with LifePlan");
        } else {
            fileContent = file.getInputStream();
            Validator.validate(fileContent);
            fileContent.reset();
        }
        lifePlan = new LifePlan(fileContent);
        getServletContext().setAttribute("lifePlan", lifePlan);
        request.setAttribute("leaves", lifePlan.getLeaves());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("Ctrl/lifePlan");
        requestDispatcher.forward(request, response);
    }

    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }

}