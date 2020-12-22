package ru.nsd.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/*")
public class ConverterNumeralController {

    ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
    Map<String, Servlet> mappingHolder = (Map<String, Servlet>)applicationContext.getBean("mappingHolder");

    @RequestMapping("/Ctrl")
    public void processRequest(@RequestParam(required = false) MultipartFile file,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {

        String name = request.getParameter("name");
        Servlet servlet = mappingHolder.get(name);
        if(file != null) {
            request.setAttribute("file", file);
        }
        servlet.service(request, response);
    }


}