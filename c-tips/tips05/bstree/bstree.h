#ifndef CTIPS_BSTREE_H
#define CTIPS_BSTREE_H

#define TREE_TYPE int

void insert(TREE_TYPE value);

TREE_TYPE *find(TREE_TYPE value);

void pre_order_traverse(void(*callback)(TREE_TYPE value));

void mid_order_traverse(void(*callback)(TREE_TYPE value));

void after_order_traverse(void(*callback)(TREE_TYPE value));

void layer_order_traverse(void(*callback)(TREE_TYPE value));

#endif //CTIPS_BSTREE_H
