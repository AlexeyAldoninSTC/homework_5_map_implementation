package app;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, null);
            map.put(null, i);
        }
        System.out.println("map.size() = " + map.size());
        System.out.println("map.get(10) = " + map.get(10));
        System.out.println("map.get(null) = " + map.get(null));
        System.out.println("map.keySet() = " + map.keySet());
        System.out.println("map.values() = " + map.values());
    }
}
