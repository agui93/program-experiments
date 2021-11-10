#include "../g_stack.h"
#include <stdio.h>

static int stackSize = 100;

GENERIC_STACK(int, _int, 100)

GENERIC_STACK(float, _float, 100)


void checkIntStack() {
    int value;

    assert(is_empty_int());
    assert(!is_full_int());

    for (int i = 0; i < stackSize; i++) {
        value = i * 10;
        push_int(value);
        assert(top_int() == value);
        assert(!is_empty_int());
        if (i == stackSize - 1) {
            assert(is_full_int());
        } else {
            assert(!is_full_int());
        }
    }


    for (int i = stackSize - 1; i >= 0; i--) {
        value = i * 10;
        assert(top_int() == value);

        pop_int();

        if (i == 0) {
            assert(is_empty_int());
        } else {
            assert(!is_empty_int());
        }
        assert(!is_full_int());
    }

    printf("SUCCESS checkIntStack\n");
}

void checkFloatStack() {
    float value;

    assert(is_empty_float());
    assert(!is_full_float());

    for (int i = 0; i < stackSize; i++) {
        value = 10.0 * i;
        push_float(value);
        assert(top_float() == value);
        assert(!is_empty_float());
        if (i == stackSize - 1) {
            assert(is_full_float());
        } else {
            assert(!is_full_float());
        }
    }

    for (int i = stackSize - 1; i >= 0; i--) {
        value = i * 10;
        assert(top_float() == value);

        pop_float();

        if (i == 0) {
            assert(is_empty_float());
        } else {
            assert(!is_empty_float());
        }
        assert(!is_full_float());
    }

    printf("SUCCESS checkFloatStack\n");
}

int main(void) {
    checkIntStack();
    checkFloatStack();
}