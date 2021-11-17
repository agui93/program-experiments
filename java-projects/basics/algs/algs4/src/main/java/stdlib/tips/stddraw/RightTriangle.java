package stdlib.tips.stddraw;

import stdlib.lib.StdDraw;

/******************************************************************************
 *  Compilation:  javac-introcs RightTriangle.java
 *  Execution:    java-introcs RightTriangle
 *
 *  Draws a right triangle and a circumscribing circle.
 *
 ******************************************************************************/

public class RightTriangle {

    public static void main(String[] args) {
        StdDraw.square(0.5, 0.5, 0.5);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(0.5, 0.5, 0.9, 0.5);
        StdDraw.line(0.9, 0.5, 0.5, 0.8);
        StdDraw.line(0.5, 0.8, 0.5, 0.5);
        StdDraw.circle(0.7, 0.65, 0.25);
    }
}
