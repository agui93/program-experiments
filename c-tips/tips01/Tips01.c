#include <stdio.h>
#include <sys/time.h>
#include <locale.h>


void testEnviron01() {
    extern char **environ;
    int i = 0;
    printf("Environ--Start\n");
    while (environ[i]) {
        printf("%s\n", environ[i++]); // prints in form of "variable=value"
    }
    printf("Environ--%d--End\n", i);
}

void testTime() {
    struct timeval tv;
    gettimeofday(&tv, NULL);
    printf("Time seconds:%ld,microseconds:%d", tv.tv_sec, tv.tv_usec);
}


int main(int argc, char **argv, char **envp) {
    char **base = argv;

    while (*base != NULL) {
        base++;
    }

    printf("\n\n");

    while (*envp != NULL) {
        envp++;
    }


    printf("\n\n");
    testEnviron01();
    printf("\n\n");
    testTime();
    printf("\n\n");
    return 0;
}