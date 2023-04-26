package com.freeloop.juc.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * InterruptDemo
 * 使用volatile实现线程中断
 * @author fj
 * @since 2023/4/26 23:00
 */
public class InterruptDemo {
    static volatile boolean isStop=false;
    static AtomicBoolean atomicBoolean=new AtomicBoolean(false);
    public static void main(String[] args) {
        //使用volatile
        //m1_volatile();
        //使用AtomicBoolean
        //m2_atomicBoolean();
        m3_interrupted();
    }

    private static void m1_volatile() {
        new Thread(()->{
            while (true){
                if (isStop){
                    System.out.println("==>isStop被修改为True");
                    break;
                }
                System.out.println("==>Hello");
            }
        },"t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(()->{
            isStop=true;
        },"t2").start();
    }

    private static void m2_atomicBoolean() {
        new Thread(()->{
            while (true){
                if (atomicBoolean.get()) {
                    System.out.println("==>atomicBoolean被修改为True");
                    break;
                }
                System.out.println("==>Hello AtomicBoolean");
            }
        },"t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(()->{
            atomicBoolean.set(true);
        },"t2").start();
    }
    //线程的命运应该有线程自己决定，不能被其他线程中断或杀死
    private static void m3_interrupted(){
        Thread t1 = new Thread(() -> {
            while (true){
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("==>interrupted()被修改为true,程序停止");
                    break;
                }
                System.out.println("==>Hello InterruptedAPI!");
            }
        },"t1");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(()->{
            //设置中断标志位为True
            t1.interrupt();
        },"t2").start();
    }
}
