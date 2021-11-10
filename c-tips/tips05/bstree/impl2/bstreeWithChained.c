#include "../bstree.h"
#include <stdlib.h>
#include <assert.h>


typedef struct TREE_NODE {
    TREE_TYPE value;
    struct TREE_NODE *left;
    struct TREE_NODE *right;
} TreeNode;
static TreeNode *tree;

void insert(TREE_TYPE value) {
    TreeNode *current;
    TreeNode **link;

    link = &tree;
    while ((current = *link) != NULL) {
        if (value < current->value) {
            link = &current->left;
        } else {
            assert(value > current->value);
            link = &current->right;
        }
    }

    TreeNode *newNode = malloc(sizeof(TreeNode));
    assert(newNode != NULL);
    newNode->value = value;
    newNode->left = NULL;
    newNode->right = NULL;

    *link = newNode;
}

TREE_TYPE *find(TREE_TYPE value) {
    TreeNode *current = tree;
    while (current != NULL) {
        if (value == current->value) {
            return current;
        }
        if (value < current->value) {
            current = current->left;
        } else {
            current = current->right;
        }
    }
    return NULL;
}

static void do_pre_order_traverse(TreeNode *node, void(*callback)(TREE_TYPE value)) {
    if (node != NULL) {
        callback(node->value);
        do_pre_order_traverse(node->left, callback);
        do_pre_order_traverse(node->right, callback);
    }
}

void pre_order_traverse(void(*callback)(TREE_TYPE value)) {
    do_pre_order_traverse(tree, callback);
}

static void do_mid_order_traverse(TreeNode *node, void(*callback)(TREE_TYPE value)) {
    if (node != NULL) {
        do_mid_order_traverse(node->left, callback);
        callback(node->value);
        do_mid_order_traverse(node->right, callback);
    }
}

void mid_order_traverse(void(*callback)(TREE_TYPE value)) {
    do_mid_order_traverse(tree, callback);
}

static void do_after_order_traverse(TreeNode *node, void(*callback)(TREE_TYPE value)) {
    if (node != NULL) {
        do_after_order_traverse(node->left, callback);
        do_after_order_traverse(node->right, callback);
        callback(node->value);
    }
}

void after_order_traverse(void(*callback)(TREE_TYPE value)) {
    do_after_order_traverse(tree, callback);
}

void layer_order_traverse(void(*callback)(TREE_TYPE value)) {
    //todo:链式结构层次遍历
}
