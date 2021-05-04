package ru.nsd.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import ru.nsd.HibernateUtil;
import ru.nsd.models.spiritModels.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@Component
public class CategoryDao {

    @PersistenceContext
    EntityManager entityManager;

    public void setCategory(Category category) {
//        EntityManagerFactory entityManagerFactory = Persistence
//                .createEntityManagerFactory("SpiritPersistence");
//        EntityManager em = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
