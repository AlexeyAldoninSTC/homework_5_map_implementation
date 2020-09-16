package library;

import java.util.Map;

public class Pair<K, V> implements Map.Entry<K, V> {

    private K key;
    private V value;

    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    void fillPair(K key, V value) {
        if (key.equals(this.key)) {
            this.value = value;
            return;
        }
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V prev = this.value;
        this.value = value;
        return prev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return this.key.equals(o);
    }

    @Override
    public int hashCode() {
        if (key == null) {
            return 0;
        }
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "[" +
                key.toString() +
                ", " + value.toString() +
                "]";
    }
}
