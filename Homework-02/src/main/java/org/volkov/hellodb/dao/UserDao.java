package org.volkov.hellodb.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.volkov.hellodb.model.User;
import org.volkov.hellodb.util.HibernateUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User>{
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public Optional<User> get(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.of(session.find(User.class, id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении данных пользователя.", e);
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении списка пользователей.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void create(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error("Ошибка при создании пользователя.", e);
        }
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error("Ошибка при изменении данных пользователя.", e);
        }
    }

    @Override
    public void delete(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error("Ошибка при удалении пользователя.", e);
        }
    }
}
