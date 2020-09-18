package library;

import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {

    Node<K, V>[] nodes = new Node[16];
    private int fillFactor = 0;
    private int size = 0;
    double loadRange = (double) 3 / 4;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int targetIndex = indexFor(key.hashCode(), nodes.length);
        return nodes[targetIndex] != null &&
                nodes[targetIndex].getPairList().contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (Node<K, V> node : nodes) {
            if (node != null) {
                MyLinkedList<Pair<K, V>> pairList = node.getPairList();
                for (int j = 0; j < pairList.getSize(); j++) {
                    if (pairList.get(j).getValue().equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if (size == 0) {
            return null;
        }
        int targetIndex = indexFor(key.hashCode(), nodes.length);
        if (nodes[targetIndex] != null &&
                nodes[targetIndex].hashCode() == key.hashCode()) {
            for (int j = 0; j < nodes[targetIndex].getPairList().getSize(); j++) {
                Pair<K, V> pair = nodes[targetIndex].getPairList().get(j);
                if (pair.getKey().equals(key)) {
                    return pair.getValue();
                }
            }
            return null;
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        V prev = this.get(key);
        Pair<K, V> input = new Pair<>(key, value);
        int targetIndex = indexFor(key.hashCode(), nodes.length);
        if (nodes[targetIndex] == null) {
            nodes[targetIndex] = new Node<>();
        }
        if (nodes[targetIndex].hashCode() != input.hashCode() && nodes[targetIndex].hashCode() == 0) {
            nodes[targetIndex].getPairList().put(input);
            fillFactor++;
            size++;
            ensureNodesCapacity();
            Arrays.sort(nodes, 0, fillFactor - 1);
            return prev;
        }
        if (nodes[targetIndex].hashCode() == input.hashCode()) {
            if (nodes[targetIndex].addPairToPairList(input)) {
                size++;
            }
            return prev;
        }
        return prev;
    }

    private void ensureNodesCapacity() {
        if ((double) fillFactor / nodes.length >= loadRange) {
            Node<K, V>[] result = new Node[nodes.length << 1];
            for (Node<K, V> node : nodes) {
                if (node != null) {
                    result[indexFor(node.hashCode(), result.length)] = node;
                }
            }
            nodes = result;
        }
    }

    @Override
    public V remove(Object key) {
        if (size == 0) {
            return null;
        }
        int targetIndex = indexFor(key.hashCode(), nodes.length);
        if (nodes[targetIndex] != null &&
                nodes[targetIndex].hashCode() == key.hashCode()) {
            for (int j = 0; j < nodes[targetIndex].getPairList().getSize(); j++) {
                Pair<K, V> pair = nodes[targetIndex].getPairList().get(j);
                if (pair.getKey().equals(key)) {
                    nodes[targetIndex].getPairList().delete(pair);
                    if (nodes[targetIndex].getPairList().getSize() == 0) {
                        nodes[targetIndex] = null;
                        fillFactor--;
                    }
                    size--;
                    return pair.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        map.forEach(this::put);
    }

    @Override
    public void clear() {
        Arrays.fill(nodes, null);
        size = 0;
        fillFactor = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Node<K, V> node : nodes) {
            if (node != null) {
                MyLinkedList<Pair<K, V>> pairList = node.getPairList();
                for (int j = 0; j < pairList.getSize(); j++) {
                    keySet.add(pairList.get(j).getKey());
                }
            }
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (Node<K, V> node : nodes) {
            if (node != null) {
                MyLinkedList<Pair<K, V>> pairList = node.getPairList();
                for (int j = 0; j < pairList.getSize(); j++) {
                    values.add(pairList.get(j).getValue());
                }
            }
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> keySet = new HashSet<>();
        for (Node<K, V> node : nodes) {
            if (node != null) {
                MyLinkedList<Pair<K, V>> pairList = node.getPairList();
                for (int j = 0; j < pairList.getSize(); j++) {
                    keySet.add(pairList.get(j));
                }
            }
        }
        return keySet;
    }

    static int indexFor(int h, int length) {
        return h & (length - 1);
    }
}
