#include "../stack.h"
#include <stdio.h>
#include <assert.h>

int main(void) {
    int stackSize = 100;
    int value;
    create_stack(stackSize);

    assert(is_empty());
    assert(!is_full());


    for (int i = 0; i < stackSize; i++) {
        value = i * 10;
        push(value);
        assert(top() == value);
        assert(!is_empty());
        assert(!is_full());
    }

    for (int i = stackSize - 1; i >= 0; i--) {
        value = i * 10;
        assert(top() == value);
        pop();
        if (i == 0) {
            assert(is_empty());
        } else {
            assert(!is_empty());
        }
        assert(!is_full());
    }

    destroy_stack();
    printf("SUCCESS\n");

}
