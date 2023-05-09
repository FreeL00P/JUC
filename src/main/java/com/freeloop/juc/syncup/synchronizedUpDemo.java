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
