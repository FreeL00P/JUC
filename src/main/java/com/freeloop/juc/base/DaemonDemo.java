package com.freeloop.juc.base;

import java.util.concurrent.TimeUnit;

/**
 * DaemonDemo
 * 用户线程和守护线程
 * @author fj
 * @since 2023/4/23 20:32
 */

/**
 * 如果用户线程全部结束意味着程序需要完成的业务都已经结束，
 * 守护线程随着JVM一同结束工作
 */
public class DaemonDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t开始运行" +
                    (Thread.currentThread().isDaemon() ? "守护线程" : "用户线程"));
            while (true) {

            }
        }, "AA");
        //设置为守护线程 必须在start前设置 否则会报 IllegalThreadStateException 异常
        t1.setDaemon(true);
        t1.start();
        //暂停几秒钟线程
        try{
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"\t主线程" +
                (Thread.currentThread().isDaemon()? "守护线程":"用户线程"));
    }
}
