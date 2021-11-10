#include "alloc.h"
#include <stdio.h>

int main(void) {
    int *m = MALLOC(10, int);
    int *p = m;
    for (int i = 0; i < 10; ++i) {
        *p = i;
        p++;
    }
    p = m;
    for (int i = 0; i < 10; ++i) {
        printf("%d ", *p);
        p++;
    }
    printf("\ninvalid:%d ", *p);
    free(m);
    return 0;
}
