package ru.nsd.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import ru.nsd.HibernateUtil;
import ru.nsd.models.Spirit;

@Component
public class SpiritDao {

    public void setItem(Spirit spirit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(spirit);
        transaction.commit();
        session.close();
    }
}
