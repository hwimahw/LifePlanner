package ru.nsd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nsd.models.Idea;
import ru.nsd.services.IdeaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/Ideas")
public class IdeasController {

    @Autowired
    IdeaService ideaService;

    @GetMapping()
    public void getAllIdeas(HttpServletRequest request, HttpServletResponse response) {
        ideaService.getAllIdeas(request, response);
    }

    @GetMapping("/{date}")
    public String getIdeasByDate(@PathVariable("date") String date,
                                 HttpServletRequest request, HttpServletResponse response) {
        ideaService.getIdeasByDate(request, response, date);
        return "ideas/ideasForm";
    }

    @GetMapping("/new")
    public String newIdeaForm() {
        return "ideas/ideasForm";
    }

    @PostMapping
    public String setIdea(@ModelAttribute("idea") @Valid Idea idea,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "ideas/ideasForm";
        }
        ideaService.setIdea(idea);
        return "ideas/ideasForm";
    }

    /*
    Redirect необходим для построения нового get запроса.
    Это нужно потому, что jsp не может обрабатывать запрос
    с методом DELETE.
    */
    @PatchMapping("/{id}")
    public String editIdea(@PathVariable("id") int id,
                           @RequestParam(value = "new_idea", required = false) String idea) {
        ideaService.editIdea(id, idea);
        return "redirect:/Ideas/new";
    }

    @DeleteMapping("/{id}")
    public String deleteIdea(@PathVariable("id") int id) {
        ideaService.deleteIdea(id);
        return "redirect:/Ideas/new";
    }
}
