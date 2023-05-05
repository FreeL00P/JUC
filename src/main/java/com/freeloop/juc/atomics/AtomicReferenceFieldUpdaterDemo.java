package com.freeloop.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 *
 * @author fj
 * @since 2023/5/5 18:50
 */

class MyVar{
    public volatile Boolean isInit=Boolean.FALSE;
    AtomicReferenceFieldUpdater<MyVar,Boolean> fieldUpdater=
            AtomicReferenceFieldUpdater.newUpdater(MyVar.class,Boolean.class,"isInit");
    public void init(MyVar myVar) {
        if (fieldUpdater.compareAndSet(myVar,Boolean.FALSE,Boolean.TRUE)) {
            System.out.println(Thread.currentThread().getName()+"\t"+"开始初始化");
            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {throw new RuntimeException(e);}
            System.out.println(Thread.currentThread().getName()+"\t"+"初始化完成");
        }else {
            System.out.println(Thread.currentThread().getName()+"\t"+"已经有线程在初始化了");
        }
    }

}
/*
    多线程并发调用一个类的初始化方法如果未被初始化过，则执行初始化工作
    只能被初始化一次，只有一个线程操作成功
 */
public class AtomicReferenceFieldUpdaterDemo {
    public static void main(String[] args) {
        MyVar myVar = new MyVar();
        for (int i = 0; i <=5 ; i++) {
            new Thread(()->{
                myVar.init(myVar);
            },String.valueOf(i)).start();
        }
    }
}
