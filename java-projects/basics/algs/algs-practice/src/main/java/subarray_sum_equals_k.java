import java.util.HashMap;
import java.util.Map;

/**
 * leetcode-560. 和为 K 的子数组
 * https://leetcode-cn.com/problems/subarray-sum-equals-k/
 *
 * @author agui93
 * @since 2021/12/19
 */
public class subarray_sum_equals_k {


    public int subarraySum(int[] nums, int k) {
        if (nums.length == 0) {
            return 0;
        }

        Map<Integer, Integer> preNums = new HashMap<>();
        preNums.put(0, 1);

        int res = 0;
        int num0_i = 0, num0_j;
        for (int num : nums) {
            num0_i = num0_i + num;
            num0_j = num0_i - k;
            if (preNums.containsKey(num0_j)) {
                res = res + preNums.get(num0_j);
            }
            preNums.put(num0_i, preNums.getOrDefault(num0_i, 0) + 1);
        }

        return res;
    }


    public static void main(String[] args) {
        subarray_sum_equals_k obj = new subarray_sum_equals_k();
        int[] a_nums = {1, 2, 3};
        System.out.println(obj.subarraySum(a_nums, 3) == 2);
        int[] b_nums = {1, 1, 1};
        System.out.println(obj.subarraySum(b_nums, 2) == 2);
        int[] c_nums = {-1, -1, 1};
        System.out.println(obj.subarraySum(c_nums, 0) == 1);
    }

}
