package com.freeloop.juc.readWriteLock;

import lombok.Data;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * LockDownGradingDemo
 *
 * @author fj
 * @since 2023/5/13 11:56
 */
/*
 锁降级 遵循读取写锁->在获取读锁->在释放写锁的次序，写锁能够降级成为读锁
 如果一个线程占有了写锁 在不释放写锁的情况下，它还能占有读锁，即写锁降级为读锁
 读没有完成的时候写锁无法获得锁，必须要等到读锁读取完毕后才有机会写
 */
public class LockDownGradingDemo {
    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        //正常 A B两个线程
        //A
       /* writeLock.lock();
        System.out.println("==>写");
        writeLock.unlock();*/

        //B
/*        readLock.lock();
        System.out.println("==>读");
        readLock.unlock();*/

        //同一个线程下
        /*
        写锁和读锁是互斥的，在一个线程下可以获取到写锁后，可以在次获取到读锁，如果获取到了读锁则不能继续获取写锁
        这是因为读写锁要保证可见性，因为如果允许读锁在被获取的情况下对写锁获取那么正在运行的其他读的线程无法感知该线程的写操作

         */
        writeLock.lock();

        System.out.println("==>写");
        System.out.println("业务");

        readLock.lock();//马上获取读锁
        System.out.println("==>读");
        writeLock.unlock();

        readLock.unlock();


    }
}
