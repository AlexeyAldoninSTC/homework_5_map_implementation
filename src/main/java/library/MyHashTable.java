package library;

import java.util.Arrays;
import java.util.Objects;

public class MyHashTable<K, V> {
    
    Node<K, V>[] nodes = new Node[10];
    private int fillFactor = 0;
    private int size = 0;
    double loadRange = 0.75;

    /**
     * Method to put certain key and value into the map
     * @param key - unique key
     * @param value - value, associated with key
     */
    public void put (K key, V value) {
        Pair<K, V> input = new Pair<>(key, value);
        int targetIndex = indexFor(key.hashCode(), nodes.length);
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
                return;
            }
            if (nodes[i].hashCode() == input.hashCode()) {
                if (nodes[targetIndex].addPairToPairList(input)) {
                    size ++;
                }
                return;
            }
        }
    }

    /**
     * Method to get value by curtain key
     * @param key - key to search by
     * @return Value if key match found or "null" if not
     */
    public V getValue(K key) {
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

    private void ensureNodesCapacity() {
        if ((double) fillFactor / nodes.length >= loadRange) {
            nodes = Arrays.copyOf(nodes, nodes.length << 1);
        }
    }

    /**
     * Method to delete the pair matching curtain value
     * @param key - key for the pair to be deleted
     * @return - true returned if deleted successfully
     */
    public boolean remove(K key) {
        if (size == 0) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (nodes[i].hashCode() == key.hashCode()) {
                for (int j = 0; j < nodes[i].getPairList().getSize(); j++) {
                    Pair<K, V> pair = nodes[i].getPairList().get(j);
                    if (pair.getKey().equals(key)) {
                        nodes[i].getPairList().delete(pair);
                        if (nodes[i].getPairList().getSize() == 0) {
                            nodes[i] = null;
                            reOrderArray(nodes);
                            fillFactor--;
                        }
                        size--;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void reOrderArray(Node<K, V>[] input) {
        for (int i = 0; i < input.length - 1; i++) {
            if (input[i] == null) {
                input[i] = input[i + 1];
                input[i + 1] = null;
            }
        }
    }

    /**
     * Method to check if currant table contains curtain key
     * @param key - key to search
     * @return - true returned if key was found
     */
    public boolean containsKey(K key) {
        for (int i = 0; i < fillFactor; i++) {
            if (nodes[i].getPairList().contains(key)) {
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyHashTable<K, V> that = (MyHashTable<K, V>) o;
        return size == that.size &&
                Arrays.equals(nodes, that.nodes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(nodes);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (Node<K, V> node : nodes) {
            if (node != null){
                builder.append(node).append(',');
            }
        }
        while (builder.charAt(builder.length() - 1) == ',') {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append(']');
        return builder.toString();
    }
    static int indexFor(int h, int length) {
        return h & (length-1);
    }
}
