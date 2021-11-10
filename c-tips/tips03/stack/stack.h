/*
 * 一个堆栈模块的接口
 */
#ifndef CTIPS_STACK_H
#define CTIPS_STACK_H


#include <stdlib.h>


//堆栈所存储的值得类型
#define STACK_TYPE int

void push(STACK_TYPE value);

void pop();

STACK_TYPE top();

/**
 * 判断是否堆栈为空
 * 如果堆栈为空，返回TRUE
 * 如果堆栈不为空，返回FALSE
 */
int is_empty();

/**
 * 判断是否堆栈已满
 * 如果堆栈已满，返回TRUE
 * 如果堆栈未满，返回FALSE
 */
int is_full();

//创建堆栈，参数指定堆栈可以保存多少个元素;不用于静态数组版的堆栈
void create_stack(size_t size);

//销毁数组，释放堆栈所使用的内存;不用于静态数组版的堆栈
void destroy_stack();

#endif //CTIPS_STACK_H