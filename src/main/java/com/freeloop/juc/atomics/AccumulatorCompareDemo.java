package com.freeloop.juc.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * AccumulatorCompareDemo
 *
 * @author fj
 * @since 2023/5/5 20:55
 */
class ClickNumber{
    int number=0;
    public synchronized void clickBySync(){
        number++;
    }
    AtomicLong atomicLong=new AtomicLong(0);
    public void clickByAtomicLong(){
        atomicLong.getAndIncrement();
    }
    LongAdder longAdder=new LongAdder();
    public void clickByLongAdder(){
        longAdder.increment();
    }
    LongAccumulator longAccumulator
            =new LongAccumulator((x,y)->x+y,0);
    public void clickByLongAccumulator(){
        longAccumulator.accumulate(1);
    }
}
//需求50个线程，每个线程点赞100w次，得到总点赞数
public class AccumulatorCompareDemo {
    public static final int int_1W=10000;
    public static final int threadNumber=50;

    public static void main(String[] args) {
        ClickNumber clickNumber=new ClickNumber();
        long startTime;
        long endTime;
        CountDownLatch latch1 = new CountDownLatch(threadNumber);
        CountDownLatch latch2 = new CountDownLatch(threadNumber);
        CountDownLatch latch3 = new CountDownLatch(threadNumber);
        CountDownLatch latch4 = new CountDownLatch(threadNumber);
        startTime=System.currentTimeMillis();
        for (int i = 1; i <=threadNumber; i++) {
            new Thread(()->{
                try {
                    for (int j = 1; j <= 100*int_1W; j++) {
                        clickNumber.clickBySync();
                    }
                }finally {
                    latch1.countDown();
                }
            },String.valueOf(i)).start();
        }
        try {latch1.await();} catch (InterruptedException e) {throw new RuntimeException(e);}
        endTime=System.currentTimeMillis();
        System.out.println("clickBySync==>消耗时间"+"\t"+(endTime-startTime)+"毫秒"+"\t"+clickNumber.number);

        startTime=System.currentTimeMillis();
        for (int i = 1; i <=threadNumber; i++) {
            new Thread(()->{
                try {
                    for (int j = 1; j <= 100*int_1W; j++) {
                        clickNumber.clickByAtomicLong();
                    }
                }finally {
                    latch2.countDown();
                }
            },String.valueOf(i)).start();
        }
        try {latch2.await();} catch (InterruptedException e) {throw new RuntimeException(e);}

        endTime=System.currentTimeMillis();
        System.out.println("clickByAtomicLong==>消耗时间"+"\t"+(endTime-startTime)+"毫秒"+"\t"+clickNumber.atomicLong.get());

        startTime=System.currentTimeMillis();
        for (int i = 1; i <=threadNumber; i++) {
            new Thread(()->{
                try {
                    for (int j = 1; j <= 100*int_1W; j++) {
                        clickNumber.clickByLongAdder();
                    }
                }finally {
                    latch3.countDown();
                }
            },String.valueOf(i)).start();
        }
        try {latch3.await();} catch (InterruptedException e) {throw new RuntimeException(e);}

        endTime=System.currentTimeMillis();
        System.out.println("clickByLongAdder==>消耗时间"+"\t"+(endTime-startTime)+"毫秒"+"\t"+clickNumber.longAdder.sum());
        startTime=System.currentTimeMillis();
        for (int i = 1; i <=threadNumber; i++) {
            new Thread(()->{
                try {
                    for (int j = 1; j <= 100*int_1W; j++) {
                        clickNumber.clickByLongAccumulator();
                    }
                }finally {
                    latch4.countDown();
                }
            },String.valueOf(i)).start();
        }
        try {latch4.await();} catch (InterruptedException e) {throw new RuntimeException(e);}
        endTime=System.currentTimeMillis();
        System.out.println("clickByLongAccumulator==>消耗时间"+"\t"+(endTime-startTime)+"毫秒"+"\t"+clickNumber.longAccumulator.get());
    }
}
