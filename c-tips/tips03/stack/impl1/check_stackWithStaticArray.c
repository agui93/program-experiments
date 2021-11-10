#include "../stack.h"
#include <stdio.h>
#include <assert.h>

int main() {
    assert(is_empty());
    assert(!is_full());

    int stackSize = 100;
    int value;

    for (int i = 0; i < stackSize; i++) {
        value = i * 10;
        push(value);
        assert(top() == value);
        assert(!is_empty());
        if (i == stackSize - 1) {
            assert(is_full());
        } else {
            assert(!is_full());
        }
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

    printf("SUCCESS\n");

}