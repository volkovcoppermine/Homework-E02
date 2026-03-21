package HW_01;

import java.util.LinkedList;
import java.util.List;

public class SimpleHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private int capacity = INITIAL_CAPACITY;
    private int size = 0;

    // Обработка коллизий: каждый элемент массива - список, где хранятся пары с одинаковым хешем
    private List<Entry<K, V>>[] buckets;

    public SimpleHashMap() {
        buckets = (List<Entry<K, V>>[]) new List[INITIAL_CAPACITY];
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    private void put(K key, V value, List<Entry<K, V>>[] temp) {
        int index = getIndex(key);
        List<Entry<K, V>> bucket = temp[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        bucket.add(new Entry<>(key, value));

        size++;
    }

    public void put(K key, V value) {
        put(key, value, buckets);

        if (size == capacity){
            resize();
        }
    }

    // Увеличиваем размер массива в 2 раза, если он заполнен
    // и рехешируем содержимое
    private void resize() {
        size = 0;
        capacity *= 2;

        List<Entry<K, V>>[] newBuckets = (List<Entry<K, V>>[]) new List[capacity];
        for (int i = 0; i < capacity; i++) {
            newBuckets[i] = new LinkedList<>();
        }

        for (List<Entry<K, V>> list: buckets) {
            for(Entry<K, V> entry: list) {
                put(entry.key, entry.value, newBuckets);
            }
        }

        buckets = newBuckets;
    }

    public V get(K key) {
        int index = getIndex(key);
        List<Entry<K, V>> bucket = buckets[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    // Удаление пары по ключу из bucket
    public void remove(K key) {
        int index = getIndex(key);
        List<Entry<K, V>> bucket = buckets[index];
        bucket.removeIf(entry -> entry.key.equals(key));
    }

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}

class Test {
    static void main() {
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>();

        for (int i = 0; i < 24; i++) {
            map.put(i, i + 10);
        }

        System.out.println(map.get(5));
        map.remove(5);
        System.out.println(map.get(5));
        System.out.println(map.get(21));
        System.out.println(map.get(25));

    }
}
