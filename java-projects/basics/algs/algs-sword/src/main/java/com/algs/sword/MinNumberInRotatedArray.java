package com.algs.sword;

import java.util.Arrays;
import java.util.Collections;

/**
 * // 面试题11：旋转数组的最小数字
 * // 题目：把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
 * // 输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如数组
 * // {3, 4, 5, 1, 2}为{1, 2, 3, 4, 5}的一个旋转，该数组的最小值为1。
 *
 * @author agui93
 * @since 2020/7/11
 */
public class MinNumberInRotatedArray {

    public static int min(int[] numbers) {
        int start = 0;
        int end = numbers.length - 1;
        int mid;

        while (true) {
            if (end == start) {
                return numbers[end];
            }
            mid = (start + end) / 2;
            if (numbers[mid] < numbers[end]) {
                end = mid;
            } else if (numbers[mid] > numbers[end]) {
                start = mid + 1;
            } else {
                end = end - 1;
            }
        }

    }

    public static void test(Integer[] numbers, int expected) {
        int[] ns = new int[numbers.length];
        int i = 0;
        for (Integer number : numbers) {
            ns[i++] = number;
        }
        int minN = min(ns);
        System.out.printf("%s Test=%d; %s\n", Arrays.toString(numbers), minN, minN == expected ? "Passed" : "Failed");
    }

    public static void main(String[] args) {
        test(Arrays.asList(2, 1).toArray(new Integer[0]), 1);
        test(Arrays.asList(2, 3, 1).toArray(new Integer[0]), 1);
        test(Arrays.asList(3, 1, 2).toArray(new Integer[0]), 1);
        test(Arrays.asList(3, 4, 1, 2).toArray(new Integer[0]), 1);
        test(Arrays.asList(3, 4, 5, 6, 1, 2).toArray(new Integer[0]), 1);
        test(Arrays.asList(5, 6, 1, 2, 3, 4).toArray(new Integer[0]), 1);

        test(Arrays.asList(2, 2, 2, 0, 1).toArray(new Integer[0]), 0);
        test(Arrays.asList(3, 4, 5, 1, 2).toArray(new Integer[0]), 1);

        test(Arrays.asList(3, 4, 5, 1, 1, 2).toArray(new Integer[0]), 1);
        test(Arrays.asList(3, 4, 5, 1, 2, 2).toArray(new Integer[0]), 1);
        test(Arrays.asList(1, 0, 1, 1, 1).toArray(new Integer[0]), 0);
        test(Arrays.asList(1, 2, 3, 4, 5).toArray(new Integer[0]), 1);
        test(Collections.singletonList(2).toArray(new Integer[0]), 2);
        test(Arrays.asList(1, 3, 5).toArray(new Integer[0]), 1);
        test(Arrays.asList(3, 1, 3).toArray(new Integer[0]), 1);
        test(Arrays.asList(1, 1, 1).toArray(new Integer[0]), 1);


    }
}
