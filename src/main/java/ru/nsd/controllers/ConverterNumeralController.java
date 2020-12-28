package ru.nsd.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import javax.xml.ws.Service;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ConverterNumeralController {

    Map<String,Servlet> mappingHolder = (Map<String, Servlet>)ContextLoader.getCurrentWebApplicationContext().getBean("mappingHolder");


    @RequestMapping("/Ctrl")
    public void processRequest(@RequestParam(required = false) MultipartFile file,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        Servlet servlet = mappingHolder.get(name);
        if (file != null) {
            request.setAttribute("file", file);
        }
        servlet.service(request, response);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ModelAndView databaseError(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }
}