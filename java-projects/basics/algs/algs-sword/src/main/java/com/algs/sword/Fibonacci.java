package com.algs.sword;

/**
 * // 面试题10：斐波那契数列
 * // 题目：写一个函数，输入n，求斐波那契（Fibonacci）数列的第n项。
 *
 * @author agui93
 * @since 2020/7/11
 */
public class Fibonacci {

    //斐波那契数列的定义 f(0)=0; f(1)=1; f(n+2)=f(n+1)+f(n) when n>=0;

    //递归方式
    public static int fibonacciByRecursion(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        return fibonacciByRecursion(n - 1) + fibonacciByRecursion(n - 2);
    }

    //非递归方式
    public static int fibonacciByCircle(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int fA = 0;
        int fB = 1;

        int temp;
        for (int c = 1; c < n; c++) {
            temp = fB;
            fB = fA + fB;
            fA = temp;
        }

        return fB;
    }

    public static void test(int n, int fR, int fC, int expected) {
        System.out.printf("%d Test fibonacciByRecursion=%d; %s\n", n, fR, fR == expected ? "Passed" : "Failed");
        System.out.printf("%d Test fibonacciByCircle=%d; %s\n", n, fC, fC == expected ? "Passed" : "Failed");

    }

    public static void main(String[] args) {
        int n = 0;
        test(n, fibonacciByRecursion(n), fibonacciByCircle(n), 0);

        n = 1;
        test(n, fibonacciByRecursion(n), fibonacciByCircle(n), 1);

        n = 2;
        test(n, fibonacciByRecursion(n), fibonacciByCircle(n), 1);


        n = 3;
        test(n, fibonacciByRecursion(n), fibonacciByCircle(n), 2);

        n = 4;
        test(n, fibonacciByRecursion(n), fibonacciByCircle(n), 3);

        n = 5;
        test(n, fibonacciByRecursion(n), fibonacciByCircle(n), 5);
        n = 6;
        test(n, fibonacciByRecursion(n), fibonacciByCircle(n), 8);
        n = 7;
        test(n, fibonacciByRecursion(n), fibonacciByCircle(n), 13);
        n = 8;
        test(n, fibonacciByRecursion(n), fibonacciByCircle(n), 21);
        n = 9;
        test(n, fibonacciByRecursion(n), fibonacciByCircle(n), 34);
        n = 10;
        test(n, fibonacciByRecursion(n), fibonacciByCircle(n), 55);


        n = 40;
        test(n, fibonacciByRecursion(n), fibonacciByCircle(n), 102334155);
    }
}
