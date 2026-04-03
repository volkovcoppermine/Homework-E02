package org.volkov.hellotest.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.volkov.hellotest.entity.UserEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<UserEntity>{
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public Optional<UserEntity> get(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.of(session.find(UserEntity.class, id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении данных пользователя.", e);
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UserEntity> query = session.createQuery("FROM UserEntity", UserEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении списка пользователей.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean create(UserEntity user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error("Ошибка при создании пользователя.", e);
            return false;
        }
    }

    @Override
    public boolean update(UserEntity user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error("Ошибка при изменении данных пользователя.", e);
            return false;
        }
    }

    @Override
    public boolean delete(UserEntity user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error("Ошибка при удалении пользователя.", e);
            return false;
        }
    }
}
