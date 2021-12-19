/**
 * leetcode-304. 二维区域和检索 - 矩阵不可变
 * https://leetcode-cn.com/problems/range-sum-query-2d-immutable/
 *
 * @author agui93
 * @since 2021/12/19
 */
public class range_sum_query_2d_immutable {

    private int[][] sumMatrix;

    public range_sum_query_2d_immutable(int[][] matrix) {

        if (matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        int rows = matrix.length;
        int columns = matrix[0].length;
        this.sumMatrix = new int[rows][columns];

        this.sumMatrix[0][0] = matrix[0][0];


        //第一行
        for (int c = 1; c < columns; c++) {
            this.sumMatrix[0][c] = this.sumMatrix[0][c - 1] + matrix[0][c];
        }

        //第一列
        for (int r = 1; r < rows; r++) {
            this.sumMatrix[r][0] = this.sumMatrix[r - 1][0] + matrix[r][0];
        }


        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < columns; c++) {
                this.sumMatrix[r][c] = this.sumMatrix[r - 1][c] + this.sumMatrix[r][c - 1] - this.sumMatrix[r - 1][c - 1] + matrix[r][c];
            }
        }

    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (this.sumMatrix.length == 0 || row1 < 0 || col1 < 0 || row1 > row2 || col1 > col2) {
            return 0;
        }

        if (row1 == 0 && col1 == 0) {
            return this.sumMatrix[row2][col2];
        }

        if (row1 == 0) {
            return this.sumMatrix[row2][col2] - this.sumMatrix[row2][col1 - 1];
        }

        if (col1 == 0) {
            return this.sumMatrix[row2][col2] - this.sumMatrix[row1 - 1][col2];
        }

        return this.sumMatrix[row2][col2] - this.sumMatrix[row2][col1 - 1] - this.sumMatrix[row1 - 1][col2] + this.sumMatrix[row1 - 1][col1 - 1];
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {3, 0, 1, 4, 2},
                {5, 6, 3, 2, 1},
                {1, 2, 0, 1, 5},
                {4, 1, 0, 1, 7},
                {1, 0, 3, 0, 5}
        };

        range_sum_query_2d_immutable instance = new range_sum_query_2d_immutable(matrix);
        for (int i = 0; i < instance.sumMatrix.length; i++) {
            System.out.print("{");
            for (int j = 0; j < instance.sumMatrix[0].length; j++) {
                System.out.print(instance.sumMatrix[i][j]);
                if (j < instance.sumMatrix[0].length) {
                    System.out.print(",");
                }
            }
            System.out.println("}");
        }


        System.out.println(instance.sumRegion(0, 0, 0, 0) == 3);
        System.out.println(instance.sumRegion(0, 0, 1, 1) == 14);
        System.out.println(instance.sumRegion(0, 0, 1, 2) == 18);
        System.out.println(instance.sumRegion(0, 0, 2, 2) == 21);

        System.out.println(instance.sumRegion(0, 1, 0, 1) == 0);
        System.out.println(instance.sumRegion(0, 1, 2, 2) == 12);
        System.out.println(instance.sumRegion(0, 1, 0, 2) == 1);
        System.out.println(instance.sumRegion(0, 1, 0, 3) == 5);

        System.out.println(instance.sumRegion(1, 0, 1, 0) == 5);
        System.out.println(instance.sumRegion(1, 0, 2, 2) == 17);

        System.out.println(instance.sumRegion(2, 1, 4, 3) == 8);
        System.out.println(instance.sumRegion(1, 1, 2, 2) == 11);
        System.out.println(instance.sumRegion(1, 2, 2, 4) == 12);

    }
}
