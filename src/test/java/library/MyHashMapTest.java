package library;

import junit.framework.AssertionFailedError;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class MyHashMapTest {

    Map<Integer, Integer> map = new MyHashMap<>();

    @Test
    public void size() {
        assertEquals(0, map.size());
        map.put(1,1);
        assertEquals(1, map.size());

    }

    @Test
    public void isEmpty() {
        assertTrue(map.isEmpty());
        map.put(1, 1);
        assertFalse(map.isEmpty());
    }

    @Test
    public void containsKey() {
        assertFalse(map.containsKey(1));
        map.put(1, 1);
        assertFalse(map.containsKey(2));
        assertTrue(map.containsKey(1));
    }

    @Test
    public void containsValue() {
        assertFalse(map.containsValue(1));
        map.put(1, 1);
        assertFalse(map.containsValue(2));
        assertTrue(map.containsValue(1));
    }

    @Test
    public void get() {
        assertNull(map.get(1));
        map.put(1, 10);
        assertEquals(Integer.valueOf(10), map.get(1));
        assertNull(map.get(2));
    }

    @Test
    public void put() {
        for (int i = 0; i < 100; i++) {
            map.put(i, i *10);
        }
        assertEquals(Integer.valueOf(300), map.get(30));
        assertNull(map.put(100, 10));
        assertEquals(101, map.size());
        assertEquals(Integer.valueOf(10), map.put(1, 20));
        assertEquals(101, map.size());
    }

    @Test
    public void remove() {
        map.put(1, 10);
        map.put(3, 30);
        assertNull(map.remove(2));
        assertEquals(2, map.size());
        assertEquals(Integer.valueOf(10), map.remove(1));
        assertEquals(1, map.size());
        assertNull(map.remove(2));
    }

    @Test
    public void putAll() {
        assertEquals(0, map.size());
        Map<Integer, Integer> map1 = new HashMap<>();
        map1.put(1, 10);
        map1.put(2, 20);
        map.putAll(map1);
        assertEquals(2, map.size());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue(20));
    }

    @Test
    public void clear() {
        map.put(1, 10);
        map.put(2, 20);
        assertEquals(2, map.size());
        map.clear();
        assertEquals(0, map.size());
        assertFalse(map.containsKey(1));
        assertFalse(map.containsValue(20));
    }

    @Test
    public void keySet() {
        map.put(1, 1);
        map.put(2, 2);
        Set<Integer> set = map.keySet();
        assertFalse(set.isEmpty());
        assertEquals(2, set.size());
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
        assertFalse(set.contains(3));
    }

    @Test
    public void values() {
        map.put(1, 10);
        map.put(2, 20);
        Collection<Integer> values = map.values();
        assertFalse(values.isEmpty());
        assertEquals(2, values.size());
        assertTrue(values.contains(10));
        assertTrue(values.contains(20));
    }

    @Test
    public void entrySet() {
        map.put(1, 10);
        map.put(2, 20);
        Set<Map.Entry<Integer, Integer>> entrySet = map.entrySet();
        assertFalse(entrySet.isEmpty());
        assertEquals(2, entrySet.size());
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            if (entry.getKey() == 1) {
                assertEquals(Integer.valueOf(10), entry.getValue());
            } else if (entry.getKey() == 2) {
                assertEquals(Integer.valueOf(20), entry.getValue());
            } else throw new AssertionFailedError("Unexpected Key found in entrySet");
        }
    }
}