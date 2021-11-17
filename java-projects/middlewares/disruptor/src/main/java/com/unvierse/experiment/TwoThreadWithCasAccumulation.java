package com.unvierse.experiment;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author agui93
 * @since 2020/7/15
 */
public class TwoThreadWithCasAccumulation implements Runnable {

    private AtomicLong value = new AtomicLong();
    private long count;
    private CountDownLatch countDownLatch;
    private ReentrantLock lock = new ReentrantLock();

    public TwoThreadWithCasAccumulation(long count, CountDownLatch countDownLatch) {
        this.count = count;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            incr();
        }
        countDownLatch.countDown();
    }

    private void incr() {
        value.incrementAndGet();
    }

    public static void test() throws InterruptedException {
        System.gc();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        long iterationCount = 1000 * 1000 * 100 * 5L;
        TwoThreadWithCasAccumulation accumulation = new TwoThreadWithCasAccumulation(iterationCount / 2, countDownLatch);
        Thread t1 = new Thread(accumulation);
        Thread t2 = new Thread(accumulation);

        long startTime = System.currentTimeMillis();
        t1.start();
        t2.start();
        countDownLatch.await();
        System.out.println("usedTime = " + (System.currentTimeMillis() - startTime));
        System.out.println(accumulation.value.intValue());
    }

    public static void main(String[] args) throws InterruptedException {
        test();
    }

}
