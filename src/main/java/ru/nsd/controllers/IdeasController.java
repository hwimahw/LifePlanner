package ru.nsd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nsd.models.Idea;
import ru.nsd.services.IdeaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/Ideas")
public class IdeasController {

    @Autowired
    IdeaService ideaService;

    @GetMapping()
    public void getAllIdeas(HttpServletRequest request, HttpServletResponse response){
        ideaService.getAllIdeas(request, response);
    }

    @GetMapping("/{date}")
    public String getIdeasByDate(@PathVariable("date") String date,
            HttpServletRequest request, HttpServletResponse response){
        ideaService.getIdeasByDate(request, response, date);
        return "ideas/ideasForm";
    }

    @GetMapping("/newg")
    public String newIdeaForm(){
        return "ideas/ideasForm";
    }

    @PostMapping
    public String setIdea(@ModelAttribute("idea") Idea idea){
        ideaService.setIdea(idea);
        return "ideas/ideasForm";
    }

    @DeleteMapping("/{ide}")
    public String editIdea(@PathVariable("ide") String id, @RequestParam("sds") String idea){
        ideaService.editIdea(Integer.parseInt(id), idea);
        return "ideas/ideasForm";
    }
}
