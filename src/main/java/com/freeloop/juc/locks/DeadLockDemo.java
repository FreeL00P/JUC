package com.freeloop.juc.locks;

import java.util.concurrent.TimeUnit;

/**
 * DeadLockDemo
 *
 * @author fj
 * @since 2023/4/26 21:29
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        final Object objectA=new Object();
        final Object objectB=new Object();
        new Thread(()->{
            synchronized (objectA) {
                System.out.println(Thread.currentThread().getName()+"\t自己持有A锁，希望获取B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (objectB) {
                    System.out.println(Thread.currentThread().getName()+"\t成功获取B锁");
                }
            }
        },"A").start();
        new Thread(()->{
            synchronized (objectB) {
                System.out.println(Thread.currentThread().getName()+"\t自己持有B锁，希望获取A锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (objectA) {
                    System.out.println(Thread.currentThread().getName()+"\t成功获取A锁");
                }
            }
        },"B").start();
    }
}
