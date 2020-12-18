package ru.nsd.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.Servlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.Map;

@Controller
@MultipartConfig
@RequestMapping("/*")
public class ConverterNumeralController {

    ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
    Map<String, Servlet> mappingHolder = (Map<String, Servlet>)applicationContext.getBean("mappingHolder");

    @PostMapping("/Ctrl")
    public void processRequest(@RequestParam MultipartFile file) throws Exception {
            String str = "aa";
        //        String name = request.getParameter("name");
//        Servlet servlet = mappingHolder.get(name);
//        Part filePart = request.getPart("file");
//
//        servlet.service(request, response);
    }


}