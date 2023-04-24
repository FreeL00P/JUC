package com.freeloop.juc.locks;

import java.util.concurrent.TimeUnit;

/**
 * Lock8Demo
 *
 * @author fj
 * @since 2023/4/24 19:40
 */
public class Lock8Demo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone1 = new Phone();
        new Thread(()->{
            phone.sendEmail();
        },"AA").start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {throw new RuntimeException(e);}
        new Thread(()->{
            phone1.sendMsg();
        },"BB").start();

    }
}
class  Phone{
    //synchronized 在静态方法上是类锁
    public static synchronized void sendEmail(){
        try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println(Thread.currentThread().getName()+"==>sendEmail");
    }
    //synchronized在普通方法上是对象锁
    public  synchronized void sendMsg(){
        System.out.println(Thread.currentThread().getName()+"==>sendMsg");
    }
    public void hello(){
        System.out.println("Hello");
    }
}
