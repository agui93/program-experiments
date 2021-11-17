package com.algs.leetcode.hot;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/two-sum/
 *
 * @author agui93
 * @since 2020/4/10
 */
public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int left = target - nums[i];
            if (map.containsKey(left)) {
                result[0] = i;
                result[1] = map.get(left);
                if (result[0] != result[1]) {
                    return result;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {

    }

}
