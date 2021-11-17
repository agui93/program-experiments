package stdlib.lib; /******************************************************************************
 *  Compilation:  javac stdlib.lib.StopwatchCPU.java
 *  Execution:    none
 *  Dependencies: none
 *
 *  A version of stdlib.lib.Stopwatch.java that measures CPU time on a single
 *  core or processor (instead of wall clock time).
 *
 ******************************************************************************/

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * The {@code stdlib.lib.StopwatchCPU} data type is for measuring
 * the CPU time used during a programming task.
 * <p>
 * See {@link Stopwatch} for a version that measures wall-clock time
 * (the real time that elapses).
 *
 * @author Josh Hug
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */

public class StopwatchCPU {
    private static final double NANOSECONDS_PER_SECOND = 1000000000;

    private final ThreadMXBean threadTimer;
    private final long start;

    /**
     * Initializes a new stopwatch.
     */
    public StopwatchCPU() {
        threadTimer = ManagementFactory.getThreadMXBean();
        start = threadTimer.getCurrentThreadCpuTime();
    }

    /**
     * Returns the elapsed CPU time (in seconds) since the stopwatch was created.
     *
     * @return elapsed CPU time (in seconds) since the stopwatch was created
     */
    public double elapsedTime() {
        long now = threadTimer.getCurrentThreadCpuTime();
        return (now - start) / NANOSECONDS_PER_SECOND;
    }
}
