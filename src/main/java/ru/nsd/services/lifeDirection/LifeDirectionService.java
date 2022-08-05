package ru.nsd.services.lifeDirection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsd.models.LifeDirection;

import java.util.List;

@Component
public class LifeDirectionService {

    private final LifeDirectionDao lifeDirectionDao;

    @Autowired
    public LifeDirectionService(LifeDirectionDao lifeDirectionDao) {
        this.lifeDirectionDao = lifeDirectionDao;
    }

    public void add(List<LifeDirection> lifeDirections) {
        lifeDirectionDao.add(lifeDirections);
    }

    public List<LifeDirection> get(Long userId) {
        return lifeDirectionDao.get(userId);
    }

    public void delete(Long userID) {
        lifeDirectionDao.delete(userID);
    }
}
