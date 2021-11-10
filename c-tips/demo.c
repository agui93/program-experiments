
#include <stdio.h>


struct sdshdr {

    // buf 中已占用空间的长度
    int len;

    // buf 中剩余可用空间的长度
    int free;

    // 数据空间
    char buf[];
};

struct sdshdrTwo {

    // buf 中已占用空间的长度
    int len;

    // buf 中剩余可用空间的长度
    int free;

    // 数据空间
    char buf[10];
};


struct sdshdrThird {

    // buf 中已占用空间的长度
    int len;

    // buf 中剩余可用空间的长度
    int free;

    // 数据空间
    char *buf;
};

int main(void) {

    printf("sdshdr结构验证\n");
    struct sdshdr hdr;
    printf("size %ld\n", sizeof(hdr));
    printf("size %ld\n", sizeof(hdr.len));
    printf("size %ld\n", sizeof(hdr.free));
    printf(" %lx\n %lx\n %lx\n %lx\n %lx\n", &hdr, &hdr.len, &hdr.free, &hdr.buf[0]);


    printf("sdshdrTwo结构验证\n");
    struct sdshdrTwo hdrTwo;
    printf("size %ld\n", sizeof(hdrTwo));
    printf("size %ld\n", sizeof(hdrTwo.len));
    printf("size %ld\n", sizeof(hdrTwo.free));
    printf("size %ld\n", sizeof(hdrTwo.buf));
    printf(" %lx\n %lx\n %lx\n %lx\n %lx\n", &hdrTwo, &hdrTwo.len, &hdrTwo.free, &hdrTwo.buf[0], &hdrTwo.buf[1]);

    printf("sdshdrThree结构验证\n");
    struct sdshdrThird hdrThree;
    printf("size %ld\n", sizeof(hdrThree));
    printf("size %ld\n", sizeof(hdrThree.len));
    printf("size %ld\n", sizeof(hdrThree.free));
    printf("size %ld\n", sizeof(hdrThree.buf));
    printf(" %lx\n %lx\n %lx\n %lx\n", &hdrThree, &hdrThree.len, &hdrThree.free, &hdrThree.buf);


    return 0;
}