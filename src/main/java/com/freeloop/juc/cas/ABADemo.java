package com.freeloop.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABADemo
 *
 * @author fj
 * @since 2023/5/5 10:42
 */
public class ABADemo {
    static AtomicInteger atomicInteger=new AtomicInteger(100);
    static AtomicStampedReference<Integer> stampedReference=new AtomicStampedReference<>(100,1);
    public static void main(String[] args) {
        new Thread(()->{
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t"+"首次版本号："+ stamp);
            try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
            stampedReference.compareAndSet(100,101,
                    stampedReference.getStamp(),stampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t"+"二次版本号："+ stampedReference.getStamp());

            stampedReference.compareAndSet(101,100,
                    stampedReference.getStamp(),stampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t"+"三次版本号："+ stampedReference.getStamp());


        },"AA").start();
        new Thread(()->{
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t"+"首次版本号："+ stamp);
            //等待AA线程发生ABA问题
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            boolean b = stampedReference.compareAndSet(100, 2022, stamp, stamp + 1);
            System.out.println(b+"\t"+stampedReference.getReference()+"\t"+stampedReference.getStamp());
        },"BB").start();
    }

    private static void abaHappen() {
        new Thread(()->{
            atomicInteger.compareAndSet(100,101);
            try {TimeUnit.MILLISECONDS.sleep(10);} catch (InterruptedException e) {throw new RuntimeException(e);}
            atomicInteger.compareAndSet(101,100);
        },"t1").start();
        new Thread(()->{
            try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException e) {throw new RuntimeException(e);}
            System.out.println(atomicInteger.compareAndSet(100,2022)+"\t"+atomicInteger.get());
        },"t2").start();
    }
}
