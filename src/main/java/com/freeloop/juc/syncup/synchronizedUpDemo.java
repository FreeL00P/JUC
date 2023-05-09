package com.freeloop.juc.syncup;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * synchronizedUpDemo
 *
 * @author fj
 * @since 2023/5/9 16:03
 */
//关闭延迟参数 演示偏向锁 -XX:BiasedLockingStartupDelay=0
public class synchronizedUpDemo {
    public static void main(String[] args)  {
        /*Object o = new Object();
        System.out.println("本应该是偏向锁");
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        o.hashCode();//没有重写，一致性哈希，重写后无效，当应该对象已经计算过identity hash code 后，他就无法进入偏向锁状态
        synchronized (o){
            System.out.println("本应该是偏向锁，但是由于一致性hash，会直接升级为轻量级锁");
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }*/
        Object o = new Object();
        synchronized (o) {
            o.hashCode();
            System.out.println("偏向锁过程中遇到一致性哈希计算请求，立马撤销偏向模式，膨胀为重量级模式");
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    private static void Heavyweight() {
        Object o = new Object();
        //当线程数较多时，会升级为重量级锁
        new Thread(()->{
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        },"t1").start();
        new Thread(()->{
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        },"t2").start();
        new Thread(()->{
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        },"t3").start();
        new Thread(()->{
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        },"t4").start();
    }

    private static void biasedLock() {
        //暂停几秒钟线程
        try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {throw new RuntimeException(e);}

        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println("================================");
        new Thread(()->{
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        },"t1").start();
    }
}
