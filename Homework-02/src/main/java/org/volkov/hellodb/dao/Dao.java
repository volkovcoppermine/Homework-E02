package org.volkov.hellodb.dao;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для реализации DAO-паттерна работы с БД.
 * Определяет методы для CRUD-операций над сущностью.
 * @param <T> тип сущности
 */
public interface Dao<T> {
    Optional<T> get(long id);
    List<T> getAll();
    void create(T t);
    void update(T t);
    void delete(T t);
}
