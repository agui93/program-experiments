#include "zmalloc.h"
#include <stdio.h>

int main(void) {

    printf("%ld\n", zmalloc_get_rss());

    printf("%ld\n", zmalloc_used_memory());

    void *ptr = zmalloc(100);
    size_t _n = (zmalloc_size(ptr));
    if (_n & (sizeof(long) - 1)) {
        _n += sizeof(long) - (_n & (sizeof(long) - 1));
    }
    printf("%ld\n", _n);


    printf("%ld\n", zmalloc_used_memory());
    void *re_ptr = zrealloc(ptr, 200);
    printf("%ld\n", zmalloc_used_memory());
    zfree(re_ptr);
    printf("%ld\n", zmalloc_used_memory());

    return 0;
}
