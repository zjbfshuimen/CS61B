package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;
        private boolean color;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
        private Node(K k, V v, Node left, Node right, boolean color) {
            this.key = k;
            this.value = v;
            this.left = left;
            this.right = right;
            this.color = color;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */
    private static boolean RED = true;
    private static boolean BLACK = false;

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp > 0) {
            return getHelper(key, p.right);
        }else if (cmp < 0) {
            return getHelper(key, p.left);
        }else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the argument of get() is null");
        }
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value, null, null, RED);
        }
        int cmp = key.compareTo(p.key);
        if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        }else if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        }else {
            p.value = value;
        }

        if (isRed(p.right) && !isRed(p.left)) {
            p = rotateLeft(p);
        }
        if (isRed(p.left) && isRed(p.left.left)) {
            p = rotateRight(p.left);
        }
        if (isRed(p.left) && isRed(p.right)) {
            flipColors(p);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
        //
        root.color = BLACK;
        size++;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }
        return x.color == RED;
    }
    private void flipColors(Node p) {
        p.color = RED;
        p.left.color = BLACK;
        p.right.color = BLACK;
    }

    private Node rotateLeft(Node p) {
        if (p == null || p.right == null) {
            throw new IllegalArgumentException();
        }
        Node temp = p.right;
        p.right = temp.left;
        temp.left = p;

        temp.color = BLACK;
        p.color = RED;

        return temp;
    }
    private Node rotateRight(Node p) {
        if (p == null || p.left == null) {
            throw new IllegalArgumentException();
        }
        Node temp = p.left;
        p.left = temp.right;
        temp.right = p;

        temp.color = BLACK;
        p.color = RED;

        return temp;
    }
    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
