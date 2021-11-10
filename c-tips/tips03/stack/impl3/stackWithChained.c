#include "../stack.h"
#include <stdlib.h>
#include <stdio.h>
#include <assert.h>


#define FALSE 0

typedef struct STACK_NODE {
    STACK_TYPE value;
    struct STACK_NODE *next;
} StackNode;

static StackNode *stack;

void create_stack(size_t size) {
}

void destroy_stack(void) {
    while (!is_empty()) {
        pop();
    }
}

void push(STACK_TYPE value) {
    StackNode *new_node;
    new_node = malloc(sizeof(StackNode));
    assert(new_node != NULL);
    new_node->value = value;
    new_node->next = stack;
    stack = new_node;
}

void pop(void) {
    StackNode *first_node;
    assert(!is_empty());
    first_node = stack;
    stack = first_node->next;
}

STACK_TYPE top(void) {
    assert(!is_empty());
    return stack->value;
}

int is_empty(void) {
    return stack == NULL;
}

int is_full(void) {
    return FALSE;
}
