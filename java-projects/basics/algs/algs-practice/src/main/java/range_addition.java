
/**
 * leetcode-370. 区间加法
 * https://leetcode-cn.com/problems/range-addition/
 *
 * @author agui93
 * @since 2022/02/07
 */
public class range_addition {

    public int[] getModifiedArray(int length, int[][] updates) {
        if (updates.length == 0) {
            return new int[length];
        }

        int[] res = new int[length];
        for (int[] update : updates) {
            res[update[0]] += update[2];
            if (update[1] + 1 < length) {
                res[update[1] + 1] -= update[2];
            }
        }

        for (int i = 1; i < length; i++) {
            res[i] += res[i - 1];
        }

        return res;
    }


    public static void main(String[] args) {
        //输入
        //5
        //[[1,3,2],[2,4,3],[0,2,-2]]
        //输出
        //[-2,0,3,5,3]
        //预期结果
        //[-2,0,3,5,3]
    }
}
