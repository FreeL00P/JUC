package com.freeloop.juc.threadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocalDemo2
 *
 * @author fj
 * @since 2023/5/7 10:18
 */
class MyData{
    ThreadLocal<Integer> threadLocalFiled=ThreadLocal.withInitial(()->0);
    public void add(){
        threadLocalFiled.set(1+threadLocalFiled.get());
    }
}

/**
 * 每个Thread都有自己的实例副本，并且该副本只能由当前线程自己使用，
 * 既然其他Thread不可访问，那就不会出现多线程共享问题、
 * 同意设置初始值。但每个线程都对这个值的修改相互独立
 */
public class ThreadLocalDemo2 {
    public static void main(String[] args) {
        MyData myData = new MyData();
        ExecutorService threadPool= Executors.newFixedThreadPool(3);
        try{
            for (int i = 0; i <10 ; i++) {
                threadPool.submit(()->{
                    try {
                        Integer before = myData.threadLocalFiled.get();
                        myData.add();
                        Integer after = myData.threadLocalFiled.get();
                        System.out.println("before=>"+before+"\t"+"after => " + after);
                    }finally {
                        //必须移除，确保每次从线程池中获取到的线程都是干净的
                        myData.threadLocalFiled.remove();
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
