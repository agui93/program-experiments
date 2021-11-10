cmake_minimum_required(VERSION 3.5)
project(c-tips C)

set(CMAKE_C_STANDARD 99)

add_executable(Demo demo.c)

add_executable(Tips01 tips01/Tips01.c)

add_executable(Tips03_checkStackWithStaticArray
        tips03/stack/impl1/stackWithStaticArray.c
        tips03/stack/impl1/check_stackWithStaticArray.c
        )
add_executable(Tips03_checkStackWithDynamicArray
        tips03/stack/impl2/stackWithDynamicArray.c
        tips03/stack/impl2/check_stackWithDynamicArray.c
        )
add_executable(Tips03_checkStackWithChained
        tips03/stack/impl3/stackWithChained.c
        tips03/stack/impl3/check_stackWithChained.c
        )

add_executable(Tips03_g_stackClient
        tips03/stack/impl4/g_stackClient.c
        )

add_executable(Tips04_checkQueueWithStackArray
        tips04/queue/impl1/queueWithStaticArray.c
        tips04/queue/impl1/check_queueWithStaticArray.c
        )

add_executable(Tips05_check_bstWithStaticArray
        tips05/bstree/impl1/bstreeWithStaticArray.c
        tips05/bstree/impl1/check_bstreeWithStaticArray.c
        )

add_executable(Tips05_check_bstWithChained
        tips05/bstree/impl2/bstreeWithChained.c
        tips05/bstree/impl2/check_bstreeWithChained.c
        )

add_executable(Tips06_01
        tips06/tips06_01.c
        )

add_executable(Tips07_check_dict
        tips07/dict/dict.c
        tips07/dict/check_dict.c
        tips07/malloc/zmalloc.c
        )

add_executable(Tips07_check_malloc
        tips07/malloc/zmalloc.c
        tips07/malloc/check_malloc.c
        )

add_executable(Tips08_check_alloc
        tips08/mem/alloc.c
        tips08/mem/check_alloc.c
        )

add_executable(Tips08_check_sort
        tips08/sort/checksort.c
        )


add_executable(Tips09_kqueue_demo_client
        tips09/kevent/multi/kevent_demo_client.c
        )

add_executable(Tips09_kqueue_demo_server
        tips09/kevent/multi/kevent_demo_server.c
        )

add_executable(Tips09_kqueue_single_server
        tips09/kevent/single/kevent_single_server.c
        )

add_executable(Tips09_kqueue_single_client
        tips09/kevent/single/kevent_single_client.c
        )


add_executable(Tips09_kqueue_file_monitor
        tips09/kevent/file/kqueue_file_monitor.c
        )

add_executable(Tips09_epoll_single_server
        tips09/epoll/single/epoll_single_server.c
        )

add_executable(Tips09_epoll_single_client
        tips09/epoll/single/epoll_single_client.c
        )

add_executable(Tips10_tcp_simple_client
        tips10/tcp/simple/tcp_simple_client.c
        )

add_executable(Tips10_tcp_simple_server
        tips10/tcp/simple/tcp_simple_server.c
        )
