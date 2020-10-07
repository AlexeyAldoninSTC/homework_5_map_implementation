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
    Map<Integer, Integer> realMap = new HashMap<>();

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
    public void putNull() {
        assertEquals(0, map.size());
        map.put(null, 1);
        map.put(1, 2);
        map.put(2, null);
        assertEquals(3, map.size());
        assertEquals(Integer.valueOf(1), map.get(null));
        assertTrue(map.containsKey(null));
        assertTrue(map.containsValue(1));
        assertTrue(map.containsValue(null));
        for (int i = 0; i < 100; i++) {
            map.put(null, i);
        }
        assertEquals(3, map.size());
        assertEquals(Integer.valueOf(99), map.get(null));
        map.clear();
        realMap.put(1, null);
        map.put(1, null);
        assertEquals(realMap.get(1), map.get(1));
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
        assertEquals(Integer.valueOf(30), map.remove(3));
        assertEquals(0, map.size());

    }

    @Test
    public void putAll() {
        Map<Object, Object> myObjectMap = new MyHashMap<>();
        Map<Object, Object> realObjectMap = new HashMap<>();
        for (int i = 0; i < 1500; i++) {
            realObjectMap.put(new Object(), new Object());
        }
        myObjectMap.putAll(realObjectMap);
        assertEquals(realObjectMap.size(), myObjectMap.size());

        Set<Map.Entry<Object, Object>> myEntrySet = myObjectMap.entrySet();
        Set<Map.Entry<Object, Object>> realEntrySet = realObjectMap.entrySet();
        assertEquals(realEntrySet.size(), myEntrySet.size());

        for (Map.Entry<Object, Object> entry : realEntrySet) {
            assertTrue(myObjectMap.containsKey(entry.getKey()));
            assertTrue(myObjectMap.containsValue(entry.getValue()));
            assertEquals(entry.getValue(), myObjectMap.get(entry.getKey()));
        }
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
        map.clear();
        for (int i = 0; i < 500; i++) {
            map.put(i, null);
            map.put(null, i);
        }
        assertEquals(501, map.keySet().size());
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
        map.clear();
        for (int i = 0; i < 500; i++) {
            map.put(i, null);
            map.put(null, i);
        }
        assertEquals(501, map.values().size());
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