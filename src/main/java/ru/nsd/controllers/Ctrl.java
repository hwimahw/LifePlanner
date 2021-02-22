package ru.nsd.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/Ctrl")
public class Ctrl {

    Map<String, Servlet> mappingHolder = (Map<String, Servlet>) ContextLoader.getCurrentWebApplicationContext().getBean("mappingHolder");


    @RequestMapping()
    public void processRequest(@RequestParam(required = false) MultipartFile file,
                               HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        Servlet servlet = mappingHolder.get(name);
        if (file != null) {
            request.setAttribute("file", file);
        }
        servlet.service(request, response);
    }

    @RequestMapping("/lifePlan")
    public String processRequest(HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException {
        return "lifePlan/lifePlanInput";
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ModelAndView databaseError(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }
}