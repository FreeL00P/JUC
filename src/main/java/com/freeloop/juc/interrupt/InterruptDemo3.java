package com.freeloop.juc.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * InterruptDemo3
 *
 * @author fj
 * @since 2023/4/27 13:22
 */
public class InterruptDemo3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName()
                            + "\t中断标志位为=>" + true + "程序停止");
                    break;
                }
                try {
                    /* t1线程休眠2s 而t2 在1s后设置t1线程的中断标志位为True 所以会抛出InterruptedException线程的中断标志被重置为false
                      调用 Thread.sleep() 这个方法可能会引起 InterruptedException 异常，
                      如果线程在调用 sleep() 方法的过程中被中断，那么 sleep() 方法会抛出 InterruptedException 异常，
                      然后清除中断标志位。因为当线程抛出 InterruptedException 异常时，线程的中断标志位会被清除，相当于被重置为 false。
                     */
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    //为什么要在异常出在调用一次interrupt()
                    /*
                      在catch块中我们需要再次调用interrupt()方法来重置中断标志位，
                      否则线程可能无法正确检测到中断信号。这是因为在捕获InterruptedException异常时，
                      该异常会清除中断标志位；如果我们不在catch块中再次调用interrupt()方法，
                      那么线程就会忽略已经发生的中断，从而无法正常退出。
                     */
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println("==>Hello InterruptDemo3");
            }

        }, "t1");
        t1.start();
        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
        //设置t1的线程中断标志位为Ture
        new Thread(t1::interrupt,"t2").start();
    }
}
