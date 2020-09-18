package library;

import java.util.Objects;

public class Node<K, V> implements Comparable<Node<K, V>> {

    private final MyLinkedList<Pair<K, V>> pairList;

    Node() {
        pairList = new MyLinkedList<>();
    }

    Node(MyLinkedList<Pair<K, V>> linkedList) {
        this.pairList = linkedList;
    }

    /**
     * Method to add provided pair to List of Pairs
     * @param pair - argument to be added
     * @return - 'true' if list size increased
     */
    boolean addPairToPairList(Pair<K, V> pair) {
        if (pairList.getSize() == 0) {
            pairList.put(pair);
            return true;
        }
        for (int i = 0; i < pairList.getSize(); i++) {
            Pair<K, V> temp = pairList.get(i);
            if (pair.getKey().equals(temp.getKey())){
                temp.fillPair(pair.getKey(), pair.getValue());
                return false;
            }
        }
        pairList.put(pair);
        return true;
    }

    MyLinkedList<Pair<K, V>> getPairList() {
        return pairList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<K, V> node = (Node) o;
        return Objects.equals(pairList, node.pairList);
    }

    @Override
    public int hashCode() {
        if (pairList.getSize() == 0) {
            return 0;
        }
        return pairList.get(0).hashCode();
    }

    @Override
    public int compareTo(Node<K, V> node) {
        return this.hashCode() - node.hashCode();
    }

    @Override
    public String toString() {
        return pairList.toString();
    }
}
