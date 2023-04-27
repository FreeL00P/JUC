package com.freeloop.juc.LockSupport;

import com.freeloop.juc.locks.ReEntryLockDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LockSupportDemo
 *
 * @author fj
 * @since 2023/4/27 14:30
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        //syncWaitNotify();
        //reentrantLockSignal();
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "=>Come in");
            //线程进入等待
            //LockSupport.park() 方法不需要加锁就能够实现线程的阻塞和解除阻塞，
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "=>被唤醒");
        });
        t1.start();
        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
        new Thread(()->{
            //精准细致的线程控制
            LockSupport.unpark(t1);
            //先唤醒后等待也是可以的
            //不会抛出InterruptedException异常
            System.out.println(Thread.currentThread().getName()+"发出通知");
        },"t2").start();
    }

    private static void reentrantLockSignal() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(()->{
            lock.lock();
            try{
                System.out.println(Thread.currentThread().getName() + "=>Come in");
                //线程进入等待
                try {condition.await();} catch (InterruptedException e) {throw new RuntimeException(e);}
                System.out.println(Thread.currentThread().getName() + "=>被唤醒");
            }finally {
                lock.unlock();
            }
        },"t1").start();
        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
        new Thread(()->{
            lock.lock();
            try{
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "=>发送通知");
            }finally {
                 lock.unlock();
            }

        },"t2").start();
    }

    private static void syncWaitNotify() {
        Object objLock=new Object();
        new Thread(()->{
            synchronized (objLock) {
                System.out.println(Thread.currentThread().getName()+"=>come in");
                try {
                    //等待 交出锁控制权
                    objLock.wait();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+"=>被唤醒");
            }
        },"t1").start();
        new Thread(()->{
            synchronized (objLock) {
                //唤醒t1
                objLock.notify();
                System.out.println(Thread.currentThread().getName()+"=>发出通知");
            }
        },"t2").start();
    }
}
