package ru.nsd.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import ru.nsd.HibernateUtil;
import ru.nsd.models.spiritModels.Spirit;

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
