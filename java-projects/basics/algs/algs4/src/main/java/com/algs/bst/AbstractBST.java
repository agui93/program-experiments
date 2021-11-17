package com.algs.bst;

/**
 * @author agui93
 * @since 2020/3/23
 */
abstract public class AbstractBST<Key extends Comparable<Key>, Value> implements BinarySearchTreeInterface<Key, Value> {

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }


}
