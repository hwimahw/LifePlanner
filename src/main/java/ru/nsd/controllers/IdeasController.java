package ru.nsd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nsd.services.IdeaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/Ideas")
public class IdeasController {

    @Autowired
    IdeaService ideaService;

    @GetMapping()
    public void getIdeas(HttpServletRequest request, HttpServletResponse response){
        ideaService.getIdeas(request, response);
    }
}
