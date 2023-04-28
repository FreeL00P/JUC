package com.freeloop.juc.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * VolatilSeeDemo
 *
 * @author fj
 * @since 2023/4/28 12:06
 */
public class gitSeeDemo {
    //main线程修改flag=false后，t1线程读取到的flag仍然为true，t1线程没有停止
    //main线程修改flag没有将其刷新到主内存，所以t1线程看不到
    //main线程将flag刷新到了主内存，但是t1线程一直读取的是自己线程工作内存中的值，没有及时去内存中获取flag的最新值
    //static  boolean flag=true;
    static volatile boolean flag=true;
    public static void main(String[] args) {
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t ---come in");
            while (flag){

            }
            System.out.println(Thread.currentThread().getName()+"\t ---flag被设置为false,程序停止");
        },"t1").start();
        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {throw new RuntimeException(e);}
        flag=false;
        System.out.println(Thread.currentThread().getName()+"\t修改完成");
    }
}
