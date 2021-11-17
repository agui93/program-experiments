package stdlib.tips.stddraw;

import stdlib.lib.StdDraw;
import stdlib.lib.StdRandom;

/**
 * 表 1.1.20 StdDraw 绘图举例
 * 随机数组
 *
 * @author agui93
 * @since 2020/3/21
 */
public class RandomArray {
    public static void main(String[] args) {
        int N = 50;
        double[] a = new double[N];
        for (int i = 0; i < N; i++)
            a[i] = StdRandom.random();
        for (int i = 0; i < N; i++) {
            double x = 1.0 * i / N;
            double y = a[i] / 2.0;
            double rw = 0.5 / N;
            double rh = a[i] / 2.0;
            StdDraw.filledRectangle(x, y, rw, rh);
        }
    }
}
