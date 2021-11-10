#ifndef CTIPS_ALLOC_H
#define CTIPS_ALLOC_H

//定义一个不易发生错误的内存分配器
#include <stdlib.h>

#define malloc  //引用接口的地方,因为这个宏定义,当使用malloc函数时会语法错误,没法直接使用malloc函数

#define MALLOC(num, type)  (type*)alloc((num)*sizeof(type))

extern void *alloc(size_t size);

#endif //CTIPS_ALLOC_H
