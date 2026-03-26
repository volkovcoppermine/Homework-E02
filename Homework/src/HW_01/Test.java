package HW_01;

public class Test {
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
