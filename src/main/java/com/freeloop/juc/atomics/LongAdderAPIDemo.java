package com.freeloop.juc.atomics;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 * LongAdderAPIDemo
 *
 * @author fj
 * @since 2023/5/5 20:38
 */
public class LongAdderAPIDemo {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();
        System.out.println(longAdder.sum());
        //<== == == == == == == == ==  ==>
        LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left+right;
            }
        }, 0);
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(3);
        System.out.println(longAccumulator.get());
    }
}
