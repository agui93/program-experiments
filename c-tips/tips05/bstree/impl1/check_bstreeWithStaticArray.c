#include "../bstree.h"
#include <stdio.h>
#include <assert.h>

/*
 *
 *                                 100
 *                               /    \
 *                             50      200
 *                           /  \      /   \
 *                         25   66    150  240
 *                        / \   / \   / \   / \
 *                       20 30 60 75
 *
 */


void callback(TREE_TYPE value) {
    printf("%d ", value);
}

int main(void) {
    int input[] = {20, 12, 25, 5, 16, 28, 9, 17, 26, 29};
//    int input[] = {100, 50, 200, 25, 66, 150, 240, 20, 30, 60, 75};
//    int input[] = {100, 50, 200, 25, 66};
    int len = sizeof(input) / sizeof(int);

    for (int j = 0; j < len; ++j) {
        insert(input[j]);
        pre_order_traverse(callback);
        printf("\n");
    }
    printf("\n");

    printf("前序遍历:");
    pre_order_traverse(callback);
    printf("\n");

    printf("中序遍历:");
    mid_order_traverse(callback);
    printf("\n");

    printf("后序遍历:");
    after_order_traverse(callback);
    printf("\n");

    printf("层次遍历:");
    layer_order_traverse(callback);
    printf("\n");

    for (int j = 0; j < len; ++j) {
        assert(input[j] == *find(input[j]));
    }

}
