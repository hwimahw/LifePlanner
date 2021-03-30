package ru.nsd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsd.dao.SpiritDao;
import ru.nsd.models.Idea;
import ru.nsd.models.Spirit;

@Component
public class SpiritService {

    @Autowired
    SpiritDao spiritDao;

    public void setItem(Spirit spirit) {
        spiritDao.setItem(spirit);
    }

}
