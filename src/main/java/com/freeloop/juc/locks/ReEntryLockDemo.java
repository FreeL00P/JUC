package com.freeloop.juc.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReEntryLockDemo
 *
 * @author fj
 * @since 2023/4/26 20:29
 */
public class ReEntryLockDemo {
    public static void main(String[] args) {
        //reEntry1();
        ReEntryLockDemo reEntryLockDemo = new ReEntryLockDemo();
        //new Thread(reEntryLockDemo::m1).start();
        ReentrantLock lock = new ReentrantLock();
        new Thread(()-> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "外层调用");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "内层调用");

                } finally {
                    //上锁必须解锁 不然会导致死锁
                    //lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        },"T1").start();
        new Thread(()-> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "外层调用");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "内层调用");

                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        },"T2").start();
    }
    private synchronized void m1(){
        System.out.println(Thread.currentThread().getName()+"\tCome in");
        m2();
        System.out.println(Thread.currentThread().getName()+"\tEnd");
    }
    private synchronized void m2(){
        System.out.println(Thread.currentThread().getName()+"\tCome in");
        m3();
        System.out.println(Thread.currentThread().getName()+"\tEnd");
    }
    private synchronized void m3(){
        System.out.println(Thread.currentThread().getName()+"\tCome in");
    }
    private static void reEntry1() {
        final Object object=new Object();
        new Thread(()->{
            synchronized (object){
                System.out.println(Thread.currentThread().getName()+"外部调用");
                synchronized (object){
                    System.out.println(Thread.currentThread().getName()+"中部调用");
                    synchronized (object){
                        System.out.println(Thread.currentThread().getName()+"内部调用");
                    }
                }
            }
        },"t1").start();
    }

}
