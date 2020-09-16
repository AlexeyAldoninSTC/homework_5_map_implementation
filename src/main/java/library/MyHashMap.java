package library;

import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {

    Node<K, V>[] nodes = new Node[10];
    private int fillFactor = 0;
    private int size = 0;
    double loadRange = 0.75;

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
        for (int i = 0; i < fillFactor; i++) {
            if (nodes[i].getPairList().contains(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < fillFactor; i++) {
            MyLinkedList<Pair<K, V>> pairList = nodes[i].getPairList();
            for (int j = 0; j < pairList.getSize(); j++) {
                if(pairList.get(j).getValue().equals(value)){
                    return true;
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
        for (int i = 0; i < size; i++) {
            if (nodes[i].hashCode() == key.hashCode()) {
                for (int j = 0; j < nodes[i].getPairList().getSize(); j++) {
                    Pair<K, V> pair = nodes[i].getPairList().get(j);
                    if (pair.getKey().equals(key)) {
                        return pair.getValue();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        V prev = this.get(key);
        Pair<K, V> input = new Pair<>(key, value);
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == null) {
                nodes[i] = new Node<>();
            }
            if (nodes[i].hashCode() != input.hashCode() && nodes[i].hashCode() == 0) {
                nodes[i].getPairList().put(input);
                fillFactor++;
                size++;
                ensureNodesCapacity();
                Arrays.sort(nodes,0, fillFactor - 1);
                return prev;
            }
            if (nodes[i].hashCode() == input.hashCode()) {
                size += nodes[i].addPairToPairList(input);
                return prev;
            }
        }
        return prev;
    }
    private void ensureNodesCapacity() {
        if ((double) fillFactor / nodes.length >= loadRange) {
            nodes = Arrays.copyOf(nodes, nodes.length << 1);
        }
    }

    @Override
    public V remove(Object key) {
        if (size == 0) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (nodes[i].hashCode() == key.hashCode()) {
                for (int j = 0; j < nodes[i].getPairList().getSize(); j++) {
                    Pair<K, V> pair = nodes[i].getPairList().get(j);
                    if (pair.getKey().equals(key)) {
                        nodes[i].getPairList().delete(pair);
                        if (nodes[i].getPairList().getSize() == 0) {
                            nodes[i] = null;
                            recollectNodesArray(i);
                            fillFactor--;
                        }
                        size--;
                        return pair.getValue();
                    }
                }
            }
        }
        return null;
    }

    private void recollectNodesArray(int indexOfNull) {
        Node<K, V>[] temp = new Node[nodes.length];
        System.arraycopy(nodes,0, temp, 0, indexOfNull);
        System.arraycopy(nodes, indexOfNull + 1, temp, indexOfNull, size - indexOfNull - 1);
        nodes = temp;
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
        for (int i = 0; i < fillFactor; i++) {
            MyLinkedList<Pair<K, V>> pairList = nodes[i].getPairList();
            for (int j = 0; j < pairList.getSize(); j++) {
                keySet.add(pairList.get(j).getKey());
            }
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (int i = 0; i < fillFactor; i++) {
            MyLinkedList<Pair<K, V>> pairList = nodes[i].getPairList();
            for (int j = 0; j < pairList.getSize(); j++) {
                values.add(pairList.get(j).getValue());
            }
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> keySet = new HashSet<>();
        for (int i = 0; i < fillFactor; i++) {
            MyLinkedList<Pair<K, V>> pairList = nodes[i].getPairList();
            for (int j = 0; j < pairList.getSize(); j++) {
                keySet.add(pairList.get(j));
            }
        }
        return keySet;
    }
}
