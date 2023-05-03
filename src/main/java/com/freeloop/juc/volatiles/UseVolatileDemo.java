package com.freeloop.juc.volatiles;

/**
 * UseVolatileDemo
 *
 * @author fj
 * @since 2023/5/3 19:44
 */
public class UseVolatileDemo {
    /**
     * 使用：当读远多于写时，综合使用内部锁和volatile变量还减少同步的开销
     * 理由：利用volatile保证读取操作的可见性，利用synchronize保证复合操作的原子性
     */
    public class Counter{
        private volatile int value;
        public int getValue(){
            return value;
        }
        public synchronized int increment(){
            return value++;
        }
    }
}
