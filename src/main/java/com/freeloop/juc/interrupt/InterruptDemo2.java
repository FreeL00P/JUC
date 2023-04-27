package com.freeloop.juc.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * InterruptDemo2
 *
 * @author fj
 * @since 2023/4/27 12:35
 */
public class InterruptDemo2 {
    public static void main(String[] args) {
        //实例方法interrupt()仅仅是设置线程中断的状态位置设置为true不会停止线程
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.print("==>" + i);
            }
            System.out.println("\nt1调用interrupt()方法后的中断标志02"+Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();
        System.out.println("==>t1线程默认的中断标志"+t1.isInterrupted());
        try {TimeUnit.MILLISECONDS.sleep(2);} catch (InterruptedException e) {throw new RuntimeException(e);}
        t1.interrupt();
        System.out.println("\nt1调用interrupt()方法后的中断标志01"+t1.isInterrupted());
        try {TimeUnit.MILLISECONDS.sleep(4000);} catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println("t1调用interrupt()方法后的中断标志03"+t1.isInterrupted());
    }
}
