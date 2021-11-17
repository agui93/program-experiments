package com.algs.bst;

/**
 * @author agui93
 * @since 2020/3/23
 */
public interface BinarySearchTreeInterface<Key extends Comparable<Key>, Value> {

    //Removes the specified key and its associated value from this symbol table (if the key is in this symbol table).
    void delete(Key key);

    //    Removes the largest key and associated value from the symbol table.
    void deleteMax();

    //    Removes the smallest key and associated value from the symbol table.
    void deleteMin();

    //    Returns the value associated with the given key.
    Value get(Key key);


    //    Does this symbol table contain the given key?
    boolean contains(Key key);


    //Returns the smallest key in the symbol table greater than or equal to key.
    Key ceiling(Key key);


    //    Returns the largest key in the symbol table less than or equal to key.
    Key floor(Key key);

    //    Returns the largest key in the symbol table less than or equal to {@code key}.
    Key floor2(Key key);


    //    Returns the height of the BST (for debugging).
    int height();

    //    Returns true if this symbol table is empty.
    boolean isEmpty();

    //    Returns all keys in the symbol table as an Iterable.
    Iterable<Key> keys();

    //    Returns all keys in the symbol table in the given range, as an Iterable.
    Iterable<Key> keys(Key lo, Key hi);


    //    Returns the keys in the BST in level order (for debugging).
    Iterable<Key> levelOrder();


    //    Returns the largest key in the symbol table.
    Key max();

    //    Returns the smallest key in the symbol table.
    Key min();

    //    Inserts the specified key-value pair into the symbol table, overwriting the old value with the new value if the symbol table already contains the specified key.
    void put(Key key, Value val);


    //    Return the number of keys in the symbol table strictly less than key.
    int rank(Key key);


    //Return the key in the symbol table of a given rank.
    Key select(int rank);


    //Returns the number of key-value pairs in this symbol table.
    int size();

    //Returns the number of keys in the symbol table in the given range.
    int size(Key lo, Key hi);


}
