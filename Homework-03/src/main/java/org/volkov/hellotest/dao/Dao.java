package org.volkov.hellotest.dao;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для реализации DAO-паттерна работы с БД.
 * Определяет методы для CRUD-операций над сущностью.
 * @param <T> тип сущности
 */
public interface Dao<T> {
    /**
     * Метод для получения сущности из БД по id.
     * @param id идентификатор сущности в БД.
     * @return пустой Optional, если сущность не найдена, иначе непустой.
     */
    Optional<T> get(long id);

    /**
     * Получить все сущности из таблицы в БД.
     * @return список возвращённых сущностей.
     */
    List<T> getAll();

    /**
     * Добавить сущность в БД.
     * @param t сущность для добавления.
     * @return успех операции.
     */
    boolean create(T t);

    /**
     * Изменить существующую запись в БД.
     * @param t сущность для обновления.
     * @return успех операции.
     */
    boolean update(T t);

    /**
     * Удалить сущность из БД.
     * @param t сущность для удаления.
     * @return успех операции.
     */
    boolean delete(T t);
}
