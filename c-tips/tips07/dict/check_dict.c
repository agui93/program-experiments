#include "dict.h"
#include <stdio.h>

int main(void) {
    dictType *type = NULL;
    void *privDataPtr = NULL;
    dict *dt = dictCreate(type, privDataPtr);
    printf("hello world");
    return 0;
}
