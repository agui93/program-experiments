/**
 * leetcode 303. 区域和检索 - 数组不可变
 * https://leetcode-cn.com/problems/range-sum-query-immutable/
 *
 * @author agui93
 * @since 2021/12/19
 */
public class range_sum_query_immutable {

    private int[] sumNums;

    public range_sum_query_immutable(int[] nums) {
        if (nums.length == 0) {
            return;
        }
        this.sumNums = new int[nums.length];
        this.sumNums[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            this.sumNums[i] = this.sumNums[i - 1] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        if (this.sumNums.length == 0 || left > right || left < 0 || right > this.sumNums.length - 1) {
            return 0;
        }
        if (left == 0) {
            return this.sumNums[right];
        }
        return this.sumNums[right] - this.sumNums[left - 1];
    }


}
