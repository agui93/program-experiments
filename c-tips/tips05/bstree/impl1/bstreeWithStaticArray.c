#include "../bstree.h"
#include <stdio.h>
#include <assert.h>

#define TREE_SIZE 100
#define ARRAY_SIZE (TREE_SIZE+1)

//节点中使用0标识元素未被使用
static TREE_TYPE tree[ARRAY_SIZE];

static int left_child(int current) {
    return current * 2;
}

static int right_child(int current) {
    return current * 2 + 1;
}

void insert(TREE_TYPE value) {
    assert(value != 0);
    int current = 1;
    while (tree[current] != 0) {
        if (value < tree[current]) {
            current = left_child(current);
        } else {
            assert(value != tree[current]);
            current = right_child(current);
        }
        assert(current < ARRAY_SIZE);
    }
    tree[current] = value;
}

TREE_TYPE *find(TREE_TYPE value) {
    int current = 1;
    while (current < ARRAY_SIZE && tree[current] != value) {
        if (value < tree[current]) {
            current = left_child(current);
        } else {
            current = right_child(current);
        }
    }

    if (current < ARRAY_SIZE) {
        return tree + current;
    } else {
        return NULL;
    }
}

static void do_pre_order_traverse(int current, void(*callback)(TREE_TYPE value)) {
    if (current < ARRAY_SIZE && tree[current] != 0) {
        callback(tree[current]);
        do_pre_order_traverse(left_child(current), callback);
        do_pre_order_traverse(right_child(current), callback);
    }
}

void pre_order_traverse(void(*callback)(TREE_TYPE value)) {
    do_pre_order_traverse(1, callback);
}

static void do_mid_order_traverse(int current, void(*callback)(TREE_TYPE value)) {
    if (current < ARRAY_SIZE && tree[current] != 0) {
        do_mid_order_traverse(left_child(current), callback);
        callback(tree[current]);
        do_mid_order_traverse(right_child(current), callback);
    }
}

void mid_order_traverse(void(*callback)(TREE_TYPE value)) {
    do_mid_order_traverse(1, callback);
}

static void do_after_order_traverse(int current, void(*callback)(TREE_TYPE value)) {
    if (current < ARRAY_SIZE && tree[current] != 0) {
        do_after_order_traverse(left_child(current), callback);
        do_after_order_traverse(right_child(current), callback);
        callback(tree[current]);
    }
}

void after_order_traverse(void(*callback)(TREE_TYPE value)) {
    do_after_order_traverse(1, callback);
}

void layer_order_traverse(void(*callback)(TREE_TYPE value)) {
    int layers[TREE_SIZE] = {0};
    layers[0] = 1;
    int layers_index = 0, layers_append_index = 0;
    int position;

    while (layers[layers_index] != 0) {
        callback(tree[layers[layers_index]]);

        position = left_child(layers[layers_index]);
        if (position < ARRAY_SIZE && tree[position] != 0) {
            layers[++layers_append_index] = position;
        }
        position = right_child(layers[layers_index]);
        if (position < ARRAY_SIZE && tree[position] != 0) {
            layers[++layers_append_index] = position;
        }
        layers_index++;
    }
}