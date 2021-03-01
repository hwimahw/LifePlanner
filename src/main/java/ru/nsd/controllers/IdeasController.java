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

    @GetMapping("/new")
    public String newIdeaForm(){

        return "ideas/ideasForm";
    }

    @PostMapping
    public String setIdea(@ModelAttribute("idea") Idea idea){
        ideaService.setIdea(idea);
        return "ideas/ideasForm";
    }

/*
Redirect необходим для построения нового get запроса.
Это нужно потому, что jsp не может обрабатывать запрос
с методом DELETE.
*/
    @DeleteMapping("/{id}")
    public String editIdea(@PathVariable("id") String id, @RequestParam("new_idea") String idea){
        ideaService.editIdea(Integer.parseInt(id), idea);
        return "redirect:/Ideas/new"; // redirect yt
    }
}
