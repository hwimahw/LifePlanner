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

    public void getIdeas(HttpServletRequest request, HttpServletResponse response){
        ideaDao.getIdeas(request, response);
    }

}
