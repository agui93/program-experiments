#include <stdio.h>
#include <stdlib.h>
#include <time.h>


static void checkDiv() {
// https://devdocs.io/c/numeric/math/div
// https://stackoverflow.com/questions/11720656/modulo-operation-with-negative-numbers
// https://en.wikipedia.org/wiki/Modulo_operation
    int data[][2] = {{5,  -3},
                     {-5, 3},
                     {-5, -3}};

    int len = sizeof(data) / sizeof(data[0]);

    for (int i = 0; i < len; ++i) {
        int numerator = data[i][0];
        int denominator = data[i][1];
        div_t dv = div(numerator, denominator);
        printf("%d %d\n", numerator, denominator);
        printf("by div     :%d %d\n", dv.quot, dv.rem);
        printf("by operator:%d %d\n", (numerator) / (denominator), (numerator) % (denominator));
        printf("\n");
    }
}

static void checkRand() {
    srand((unsigned int) time(NULL));
    printf("rands between 0 and 100:%d %d\n", rand() % 100, rand() % 100);
}

int main(void) {
    checkDiv();
    checkRand();
}