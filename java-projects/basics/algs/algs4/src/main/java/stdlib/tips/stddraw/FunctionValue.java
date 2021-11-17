package stdlib.tips.stddraw;

import stdlib.lib.StdDraw;

/**
 * 表 1.1.20 StdDraw 绘图举例
 * 函数值
 *
 * @author agui93
 * @since 2020/3/21
 */
public class FunctionValue {

    public static void main(String[] args) {
        int N = 100;
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N * N);
        StdDraw.setPenRadius(.01);
        for (int i = 1; i <= N; i++) {
            StdDraw.point(i, i);
            StdDraw.point(i, i * i);
            StdDraw.point(i, i * Math.log(i));
        }
    }
}
