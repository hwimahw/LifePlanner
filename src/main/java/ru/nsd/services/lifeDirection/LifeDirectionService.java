package ru.nsd.services.lifeDirection;

import ru.nsd.models.LifeDirection;

import java.util.List;

public class LifeDirectionService {
    private final LifeDirectionDao lifeDirectionDao = new LifeDirectionDao();

    public void add(List<LifeDirection> lifeDirections){
        lifeDirectionDao.add(lifeDirections);
    }

    public List<LifeDirection> get(Long userId){
       return lifeDirectionDao.get(userId);
    }
}
