#include "alloc.h"
#include <stdio.h>

#undef malloc

void *alloc(size_t size) {
    void *new_mem;
    new_mem = malloc(size);
    if (new_mem == NULL) {
        printf("Out of memory\n");
        exit(1);
    }

    return new_mem;
}


