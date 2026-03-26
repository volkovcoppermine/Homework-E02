package HW_01;

/**
 * Интерфейс, определяющий мой собственный ассоциативный массив
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public interface MyHashMap<K, V> {
    /**
     * Добавить элемент в массив
     * @param key ключ
     * @param value значение
     */
    void put(K key, V value);

    /**
     * Получить элемент массива по ключу
     * @param key ключ
     * @return значение, соответствующее ключу
     */
    V get(K key);

    /**
     * Удалить элемент по ключу
     * @param key ключ
     */
    void remove(K key);
}
