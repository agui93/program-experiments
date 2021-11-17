package com.unvierse.experiment;

/**
 * @author agui93
 * @since 2020/7/15
 */
public class SingleThreadAccumulation extends AbstractSingleThreadAccumulation {

    private long value;

    private long count;

    public SingleThreadAccumulation(long count) {
        this.count = count;
    }

    @Override
    protected long runAccumulation() throws Exception {
        value = 0;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            value++;
        }
        return System.currentTimeMillis() - startTime;
    }


}
