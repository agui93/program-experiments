package com.unvierse.experiment;

/**
 * @author agui93
 * @since 2020/7/15
 */
public abstract class AbstractSingleThreadAccumulation {
    public static final int RUNS = 1;


    protected void testImplementations() throws Exception {
        System.out.println("Starting Single tests");
        for (int i = 0; i < RUNS; i++) {
            System.gc();
            long usedTime = runAccumulation();
            System.out.format("Run %d, %,d millisecond%n", i, usedTime);
        }
        System.out.println();
    }

    protected abstract long runAccumulation() throws Exception;

}
