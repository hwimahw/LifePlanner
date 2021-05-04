package ru.nsd.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.nsd.models.spiritModels.Spirit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class SpiritDao {

    @PersistenceContext
    EntityManager entityManager;


    @Transactional
    public void setItem(Spirit spirit) {
        entityManager.persist(spirit);
        entityManager.close();
    }
}
