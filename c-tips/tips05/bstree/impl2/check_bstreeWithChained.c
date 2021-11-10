#include "../bstree.h"
#include <stdio.h>
#include <assert.h>

void callback(TREE_TYPE value) {
    printf("%d ", value);
}

int main(void) {
//前序遍历:20 12 5 9 16 17 25 28 26 29 
//中序遍历:5 9 12 16 17 20 25 26 28 29 
//后序遍历:9 5 17 16 12 26 29 28 25 20 
//层次遍历:20 12 25 5 16 28 9 17 26 29 
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