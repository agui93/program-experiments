package com.algs.sword;

import java.util.Arrays;

/**
 * // 面试题3（一）：找出数组中重复的数字
 * // 题目：在一个长度为n的数组里的所有数字都在0到n-1的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，
 * // 也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。例如，如果输入长度为7的数组{2, 3, 1, 0, 2, 5, 3}，
 * // 那么对应的输出是重复的数字2或者3。
 *
 * <p>
 * 在一个数组长度为n的数组里，所有数字都在 0 ~ n-1 范围内，
 * 数组中某些数字是重复的，但不知道几个数字重复了，也不知道每个数字重复了多少次，
 * 请找出数组中重复的数字。
 * <p>
 * 例如，如果输入长度为7的数组{2，3，1，0，2，5，3}，那么对应的输出是重复的数字2或者是3。
 * <p>
 * 方法1:排序   时间O(N*logN)  空间O(1)
 * 方法2:hash  时间O(N)  空间O(N)
 * 方法3:针对题目中数字范围的特点
 *
 * @author agui93
 * @since 2020/7/5
 */
public class DuplicationInArray {

    //假定n的范围:n <= Integer.MAX_VALUE

    /**
     * @param arrays 输入
     * @return 重复的数字, 或者不存在重复的原因:-1,-2,-3分别表示不同含义
     */
    public static int findDuplicatedNum(int[] arrays) {
        if (arrays == null || arrays.length == 0) {
            return -1;
        }

        //数据合法性验证
        for (int num : arrays) {
            if (num < 0 || num >= arrays.length) {
                return -2;
            }
        }

        int temp;
        for (int i = 0; i < arrays.length; i++) {
            while (i != arrays[i]) {
                //System.out.println("debug log: i=" + i + " ,arrays= " + Arrays.toString(arrays));
                if (arrays[i] == arrays[arrays[i]]) {
                    return arrays[i];
                }

                //交互位置i和arrays[i]位置的数值
                temp = arrays[i];
                arrays[i] = arrays[arrays[i]];
                arrays[temp] = temp;
            }
        }

        return -3;
    }


    public static void useCase(int[] arrays) {
        int duplicationNum = findDuplicatedNum(arrays);


        System.out.print("arrays = " + Arrays.toString(arrays) + "; ");
        if (duplicationNum < 0) {
            System.out.println("no duplicated num because of " + duplicationNum);
        } else {
            System.out.println("one duplicated num is " + duplicationNum);
        }
    }

    public static void main(String[] args) {


        useCase(null);

        //空数组
        int[] arrays1 = {};
        useCase(arrays1);

        //数值范围不对
        int[] arrays2 = {2, 3, 1, 7, 2, 5, 3};
        useCase(arrays2);
        int[] arrays3 = {2, 3, 1, 0, -2, 5, 3};
        useCase(arrays3);

        //重复一个
        int[] arrays4 = {2, 3, 1, 0, 2, 5, 3};
        useCase(arrays4);

        //重复2个
        int[] arrays5 = {2, 3, 1, 0, 4, 4, 3, 2};
        useCase(arrays5);


    }


}
