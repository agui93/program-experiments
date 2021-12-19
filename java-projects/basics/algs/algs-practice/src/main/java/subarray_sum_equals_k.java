
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


        return 0;
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
