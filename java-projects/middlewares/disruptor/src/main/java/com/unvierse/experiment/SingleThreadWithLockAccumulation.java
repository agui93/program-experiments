package com.unvierse.experiment;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author agui93
 * @since 2020/7/15
 */
public class SingleThreadWithLockAccumulation extends AbstractSingleThreadAccumulation {

    private long value;
    private long count;
    private final Lock lock = new ReentrantLock();

    private SingleThreadWithLockAccumulation(long count) {
        this.count = count;
    }


    @Override
    protected long runAccumulation() throws Exception {
        value = 0;
        long startTime = System.currentTimeMillis();

//        synchronized (SingleThreadWithLockAccumulation.class) {
//            for (int i = 0; i < count; i++) {
//                value++;
//            }
//        }

        for (int i = 0; i < count; i++) {
            lock.lock();
            try {

                this.value++;

            } finally {
                lock.unlock();
            }
        }
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) throws Exception {
        long iterationCount = 1000 * 1000 * 100 * 5L;
        new SingleThreadAccumulation(iterationCount).testImplementations();
        new SingleThreadWithLockAccumulation(iterationCount).testImplementations();

        TwoThreadWitchLockAccumulation.test();
        TwoThreadWithCasAccumulation.test();
    }
}
