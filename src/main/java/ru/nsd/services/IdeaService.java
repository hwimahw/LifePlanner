package ru.nsd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ru.nsd.dao.IdeaDao;
import ru.nsd.models.Idea;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class IdeaService {
    @Autowired
    private IdeaDao ideaDao;

    public IdeaService(IdeaDao ideaDao){
        this.ideaDao = ideaDao;
    }

    public void getAllIdeas(HttpServletRequest request, HttpServletResponse response){
        ideaDao.getAllIdeas(request, response);
    }

    public void getIdeasByDate(HttpServletRequest request, HttpServletResponse response,
                        String date){
        ideaDao.getIdeasByDate(request, response, date);
    }

    public void setIdea(Idea idea) {
        ideaDao.setIdea(idea);
    }


    public void editIdea(int id, String idea) {
        ideaDao.editIdea(id, idea);
    }

    public void deleteIdea(int id) {
        ideaDao.deleteIdea(id);
    }


}
