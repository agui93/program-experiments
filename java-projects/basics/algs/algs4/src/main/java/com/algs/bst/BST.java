package com.algs.bst;

/**
 * @author agui93
 * @since 2020/3/23
 */
public class BST<Key extends Comparable<Key>, Value> extends AbstractBST<Key, Value> {

    private Node root;             // root of BST

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public BST() {
    }


    @Override
    public void delete(Key key) {

    }

    @Override
    public void deleteMax() {

    }

    @Override
    public void deleteMin() {

    }

    @Override
    public Value get(Key key) {
        return get(this.root, key);
    }

    private Value get(Node x, Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get(key) is null");
        }

        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x.val;
        } else if (cmp < 0) {
            return get(x.left, key);
        } else {
            return get(x.right, key);
        }
    }

    @Override
    public Key ceiling(Key key) {
        return null;
    }

    @Override
    public Key floor(Key key) {
        return null;
    }

    @Override
    public Key floor2(Key key) {
        return null;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public Iterable<Key> keys() {
        return null;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        return null;
    }

    @Override
    public Iterable<Key> levelOrder() {
        return null;
    }

    @Override
    public Key max() {
        return null;
    }

    @Override
    public Key min() {
        return null;
    }

    @Override
    public void put(Key key, Value val) {

    }

    @Override
    public int rank(Key key) {
        return 0;
    }

    @Override
    public Key select(int rank) {
        return null;
    }

    @Override
    public int size() {
        if (this.root == null) {
            return 0;
        }
        return this.root.size;
    }


    @Override
    public int size(Key lo, Key hi) {
        return 0;
    }
}
