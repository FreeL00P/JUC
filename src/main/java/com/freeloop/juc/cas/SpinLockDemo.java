package com.freeloop.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * SpinLockDmeo
 *
 * @author fj
 * @since 2023/5/5 9:41
 */
/*
    实现一个自旋锁
    好处：循环比较获取没有类似wait的阻塞
    通过CAS操作完成自旋锁，A线程先进来调用mylock方法自己持有锁5秒钟，
    B进来发现有线程持有锁，所以只能自旋等待，直到A释放锁
 */
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    public void lock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }
    public void unlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t"+"---task over unlock...");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(()->{
            spinLockDemo.lock();
            //暂停几秒钟线程
            try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {throw new RuntimeException(e);}
            spinLockDemo.unlock();
        },"A").start();
        try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
        new Thread(()->{
            spinLockDemo.lock();
            spinLockDemo.unlock();
        },"B").start();
    }
}
