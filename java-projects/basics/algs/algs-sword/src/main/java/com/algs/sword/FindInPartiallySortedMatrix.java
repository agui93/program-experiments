package com.algs.sword;

import java.util.Arrays;
import java.util.List;

/**
 * // 面试题4：二维数组中的查找
 * // 题目：在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按
 * // 照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个
 * // 整数，判断数组中是否含有该整数。
 *
 * @author agui93
 * @since 2020/7/11
 */
public class FindInPartiallySortedMatrix {

    public static boolean findNumberIn2DArray(int[][] matrix, int target) {
        //数据合法性校验
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }


        for (int[] ints : matrix) {
            //判断每行的元素数量是否相同
            if (ints.length != matrix[0].length) {
                return false;
            }
            //判断行维度是否是递增
            for (int j = 1; j < ints.length; j++) {
                if (ints[j] < ints[j - 1]) {
                    return false;
                }
            }
        }

        //判断列维度的元素是否递增
        for (int c = 0; c < matrix[0].length; c++) {
            for (int r = 1; r < matrix.length; r++) {
                if (matrix[r][c] < matrix[r - 1][c]) {
                    return false;
                }
            }
        }

        int searchRow = 0;
        int searchColumn = matrix[0].length - 1;
        while (true) {
            //在行维度从右向左查找,第一个不大于target的位置
            while (matrix[searchRow][searchColumn] > target) {
                searchColumn--;
                if (searchColumn < 0) {
                    return false;
                }
            }
//            System.out.println("debugA ;for target=" + target + "; searchRow=" + searchRow + "; searchColumn=" + searchColumn);
            if (matrix[searchRow][searchColumn] == target) {
                return true;
            }


            //在列维度从上向下查找,第一个不小于target的位置
            while (matrix[searchRow][searchColumn] < target) {
                searchRow++;
                if (searchRow >= matrix.length) {
                    return false;
                }
            }
//            System.out.println("debugB ;for target=" + target + "; searchRow=" + searchRow + "; searchColumn=" + searchColumn);
            if (matrix[searchRow][searchColumn] == target) {
                return true;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };

        List<Integer> targets = Arrays.asList(1, 15, 18, 30, 5, 20, -1, 31);
        List<Boolean> expectHits = Arrays.asList(true, true, true, true, true, false, false, false);
        for (int i = 0; i < targets.size(); i++) {
            int target = targets.get(i);
            boolean expectHit = expectHits.get(i);

            if (expectHit == findNumberIn2DArray(matrix, target)) {
                System.out.println("for " + target + ",hit=" + expectHit);
            } else {
                System.out.println("error for target=" + target);
            }
            System.out.println();

        }


    }

}
