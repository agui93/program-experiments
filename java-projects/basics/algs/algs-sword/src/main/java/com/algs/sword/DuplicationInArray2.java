package com.algs.sword;

import java.util.Arrays;

/**
 * 不修改数组找出重复的数字
 * <p>
 * 在一个长度为n+1的数组里的所有数字都在1~n的范围内，
 * 所以数组中至少有一个数字是重复的。
 * 请找出数组中任意一个重复的数字，但是不能修改输入的数组。
 * 例如，如果输入长度为8的数组{2,3,5,4,3,2,6,7}，那么对应的输出是重复的数字2或者3。
 *
 * @author agui93
 * @since 2020/7/5
 */
public class DuplicationInArray2 {

    public static int findDuplicatedNum(int[] arrays) {
        //数据合法性校验
        if (arrays == null || arrays.length < 2) {
            return -1;
        }
        for (int e : arrays) {
            if (e < 1 || e > arrays.length - 1) {
                return -2;
            }
        }

        int start = 1;
        int end = arrays.length - 1;
        int middle;

        while (start <= end) {
            middle = start + (end - start) / 2;

            System.out.println("debugLog length=" + arrays.length + " ,start=" + start + " ,end=" + end + " ,middle=" + middle);


            if (existsDuplication(arrays, start, middle)) {
                end = middle;
            } else {
                start = middle + 1;
            }

            if (end - start == 0) {
                return start;
            }
        }

        return -3;
    }


    public static boolean existsDuplication(int[] arrays, int start, int end) {
        int times = 0;
        for (int array : arrays) {
            if (array >= start && array <= end) {
                times++;
            }
        }
        return times > (end - start + 1);
    }


    public static void useCase(int[] arrays, int... expectNums) {
        int result = findDuplicatedNum(arrays);

        System.out.print("arrays = " + Arrays.toString(arrays) + "; ");
        if (expectNums == null || expectNums.length == 0) {
            System.out.println("expectNums param wrong!!!!");
            return;
        }
        System.out.print("expectNums = " + Arrays.toString(expectNums) + "; ");

        //校验数据
        boolean existExpectNum = false;
        for (int expectNum : expectNums) {
            if (result == expectNum) {
                existExpectNum = true;
                break;
            }
        }
        if (!existExpectNum) {
            System.out.println("Application method findDuplicatedNum wrong!!!!!,return " + result);
            return;
        }


        if (result < 0) {
            if (result == -3) {
                System.out.println("Application method findDuplicatedNum wrong!!!!!");
            } else {
                System.out.println("no duplicated num because of " + result);
            }
        } else {
            System.out.println("one duplicated num is " + result);
        }
        System.out.println();
        System.out.println();
    }


    public static void main(String[] args) {

        useCase(null, -1);

        //空数组
        int[] arrays1 = {};
        useCase(arrays1, -1);

        int[] arrays2 = {1};
        useCase(arrays2, -1);

        int[] arrays3 = {1, 2};
        useCase(arrays3, -2);

        int[] arrays4 = {1, 1};
        useCase(arrays4, 1);


        int[] arrays5 = {1, 1, 2};
        useCase(arrays5, 1);
        int[] arrays6 = {1, 2, 1};
        useCase(arrays6, 1);
        int[] arrays7 = {2, 1, 1};
        useCase(arrays7, 1);
        int[] arrays8 = {1, 1, 1};
        useCase(arrays8, 1);
        int[] arrays9 = {2, 2, 2};
        useCase(arrays9, 2);
        int[] arrays10 = {1, 2, 2};
        useCase(arrays10, 2);
        int[] arrays11 = {2, 1, 2};
        useCase(arrays11, 2);
        int[] arrays12 = {2, 2, 1};
        useCase(arrays12, 2);


        int[] arrays13 = {1, 2, 2, 2};
        useCase(arrays13, 2);
        int[] arrays14 = {1, 3, 2, 2};
        useCase(arrays14, 2);
        int[] arrays15 = {1, 1, 3, 3};
        useCase(arrays15, 1, 3);
        int[] arrays16 = {1, 1, 2, 2};
        useCase(arrays16, 1, 2);
        int[] arrays17 = {1, 3, 3, 3};
        useCase(arrays17, 3);


        int[] arrays18 = {2, 3, 5, 4, 3, 2, 6, 7};
        useCase(arrays18, 2, 3);
    }
}
