package ru.nsd.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import ru.nsd.HibernateUtil;
import ru.nsd.models.spiritModels.Spirit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Component
public class SpiritDao {

    public void setItem(Spirit spirit) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("SpiritPersistence");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(spirit);
        em.getTransaction().commit();
        em.close();
    }
}
