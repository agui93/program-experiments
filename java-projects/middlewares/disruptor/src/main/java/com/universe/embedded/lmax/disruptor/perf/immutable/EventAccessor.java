package com.universe.embedded.lmax.disruptor.perf.immutable;

public interface EventAccessor<T>
{
    T take(long sequence);
}
