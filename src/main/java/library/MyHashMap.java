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
        int targetIndex = indexFor(getHash(key), nodes.length);
        return nodes[targetIndex] != null &&
                nodes[targetIndex].getPairList().contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (Node<K, V> node : nodes) {
            if (node != null) {
                MyLinkedList<Pair<K, V>> pairList = node.getPairList();
                for (int j = 0; j < pairList.getSize(); j++) {
                    V pairValue = pairList.get(j).getValue();
                    if (pairValue == value || pairValue.equals(value)) {
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
        int keyHash = getHash(key);
        int targetIndex = indexFor(keyHash, nodes.length);
        if (nodes[targetIndex] != null) {
            for (int j = 0; j < nodes[targetIndex].getPairList().getSize(); j++) {
                Pair<K, V> pair = nodes[targetIndex].getPairList().get(j);
                K pairKey = pair.getKey();
                if (pairKey == key || pairKey.equals(key)) {
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
        int keyHash = getHash(key);
        int targetIndex = indexFor(keyHash, nodes.length);
        if (nodes[targetIndex] == null) {
            nodes[targetIndex] = new Node<>();
            fillFactor++;
        }
        if (nodes[targetIndex].addPairToPairList(input)){
            size++;
        }
        ensureCapacity();
        return prev;
    }

    /**
     * increases this nodes array length and refills new array with all pairs.
     */
    private void ensureCapacity() {
        if ((double) fillFactor / nodes.length >= loadRange) {
            Node<K, V>[] temp = nodes;
            nodes = new Node[nodes.length << 1];
            fillFactor = 0;
            for (Node<K, V> node : temp) {
                if (node != null) {
                    for (int i = 0; i < node.getPairList().getSize(); i++) {
                        Pair<K, V> pair = node.getPairList().get(i);
                        int keyHash = getHash(pair.getKey());
                        int targetIndex = indexFor(keyHash, nodes.length);
                        if (nodes[targetIndex] == null) {
                            nodes[targetIndex] = new Node<>();
                            fillFactor++;
                        }
                        nodes[targetIndex].addPairToPairList(pair);
                    }
                }
            }
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
    static int getHash(Object o) {
        return o == null ? 0 : o.hashCode();
    }
}
