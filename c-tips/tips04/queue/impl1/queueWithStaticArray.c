#include "../queue.h"

#define QUEUE_SIZE 100
#define ARRAY_SIZE (QUEUE_SIZE+1)

static QUEUE_TYPE queue[ARRAY_SIZE];//存储队列元素的数组
static size_t front = 0;//指向队列头
static size_t rear = 0;//指向队列尾



