/*
 * Copyright 2011 LMAX Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.universe.embedded.lmax.disruptor.perf.workhandler;

import com.lmax.disruptor.*;
import com.universe.embedded.lmax.disruptor.perf.support.EventCountingQueueProcessor;
import com.universe.embedded.lmax.disruptor.perf.support.EventCountingWorkHandler;
import com.universe.embedded.lmax.disruptor.perf.support.ValueEvent;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.universe.embedded.lmax.disruptor.perf.util.PaddedLong;
import com.universe.embedded.lmax.disruptor.perf.AbstractPerfTestDisruptor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static com.universe.embedded.lmax.disruptor.perf.support.PerfTestUtil.failIfNot;

public final class OneToThreeWorkerPoolThroughputTest
    extends AbstractPerfTestDisruptor
{
    private static final int NUM_WORKERS = 3;
    private static final int BUFFER_SIZE = 1024 * 8;
    private static final long ITERATIONS = 1000L * 1000L * 100L;
    private final ExecutorService executor = Executors.newFixedThreadPool(NUM_WORKERS, DaemonThreadFactory.INSTANCE);

    private final PaddedLong[] counters = new PaddedLong[NUM_WORKERS];

    {
        for (int i = 0; i < NUM_WORKERS; i++)
        {
            counters[i] = new PaddedLong();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private final BlockingQueue<Long> blockingQueue = new LinkedBlockingQueue<Long>(BUFFER_SIZE);
    private final EventCountingQueueProcessor[] queueWorkers = new EventCountingQueueProcessor[NUM_WORKERS];

    {
        for (int i = 0; i < NUM_WORKERS; i++)
        {
            queueWorkers[i] = new EventCountingQueueProcessor(blockingQueue, counters, i);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private final EventCountingWorkHandler[] handlers = new EventCountingWorkHandler[NUM_WORKERS];

    {
        for (int i = 0; i < NUM_WORKERS; i++)
        {
            handlers[i] = new EventCountingWorkHandler(counters, i);
        }
    }

    private final RingBuffer<ValueEvent> ringBuffer =
        RingBuffer.createSingleProducer(
            ValueEvent.EVENT_FACTORY,
            BUFFER_SIZE,
            new YieldingWaitStrategy());

    private final WorkerPool<ValueEvent> workerPool =
        new WorkerPool<ValueEvent>(
            ringBuffer,
            ringBuffer.newBarrier(),
            new FatalExceptionHandler(),
            handlers);

    {
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected int getRequiredProcessorCount()
    {
        return 4;
    }

    @Override
    protected long runDisruptorPass() throws InterruptedException
    {

        resetCounters();
        RingBuffer<ValueEvent> ringBuffer = workerPool.start(executor);
        long start = System.currentTimeMillis();

        for (long i = 0; i < ITERATIONS; i++)
        {
            long sequence = ringBuffer.next();
            ringBuffer.get(sequence).setValue(i);
            ringBuffer.publish(sequence);
        }

        workerPool.drainAndHalt();
        long opsPerSecond = (ITERATIONS * 1000L) / (System.currentTimeMillis() - start);

        failIfNot(ITERATIONS, sumCounters());

        return opsPerSecond;
    }

    private void resetCounters()
    {
        for (int i = 0; i < NUM_WORKERS; i++)
        {
            counters[i].set(0L);
        }
    }

    private long sumCounters()
    {
        long sumJobs = 0L;
        for (int i = 0; i < NUM_WORKERS; i++)
        {
            sumJobs += counters[i].get();
        }

        return sumJobs;
    }

    public static void main(String[] args) throws Exception
    {
        new OneToThreeWorkerPoolThroughputTest().testImplementations();
    }
}
