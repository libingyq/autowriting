package com.dinfo.autowriting.core.id;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 分布式全局递增唯一id生成器
 * snowflake算法的cas实现，期望获得更好的性能
 *
 * @author yangxf
 */
public final class SnowflakeIdGenerator implements IdGenerator {

    /**
     * 工作节点id
     */
    private final long workerId;

    private final AtomicLong sequence = new AtomicLong();

    private final AtomicLong lastTime = new AtomicLong(System.currentTimeMillis());

    public SnowflakeIdGenerator(long workerId) {
        if (workerId < 0) {
            throw new IllegalArgumentException("workerId must greater than or equals 0");
        }

        if (workerId > WORKER_ID_MAX_VALUE) {
            throw new IllegalArgumentException("workerId must less than " + WORKER_ID_MAX_VALUE);
        }

        this.workerId = workerId;
    }

    @Override
    public long nextId() {
        long currentTimeMillis = System.currentTimeMillis(),
                lastMillis = lastTime.get(),
                nextSequence = sequence.incrementAndGet();

        if (currentTimeMillis > lastMillis || nextSequence > SEQUENCE_MAX_VALUE) {
            for (; ; ) {
                if (currentTimeMillis > lastMillis &&
                        lastTime.compareAndSet(lastMillis, currentTimeMillis)) {
                    synchronized (this) {
                        if (sequence.get() >= SEQUENCE_MAX_VALUE) {
                            sequence.set(0);
                        }
                        nextSequence = sequence.incrementAndGet();
                        lastMillis = currentTimeMillis;
                    }
                    break;
                }
                lastMillis = lastTime.get();
                currentTimeMillis = System.currentTimeMillis();
            }
        }

        return ((lastMillis - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) |
                (workerId << WORKER_ID_LEFT_SHIFT_BITS) |
                (nextSequence & SEQUENCE_MASK);
    }

    private static final long EPOCH;

    private static final long SEQUENCE_BITS = 12L;

    private static final long WORKER_ID_BITS = 10L;

    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

    private static final long WORKER_ID_MAX_VALUE = (1L << WORKER_ID_BITS) - 1;

    private static final long SEQUENCE_MAX_VALUE = SEQUENCE_MASK;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.NOVEMBER, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
    }

}
