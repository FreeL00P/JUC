package com.freeloop.juc.cf;

import java.util.concurrent.*;

/**
 * FutureThreadPoolDemo
 *
 * @author fj
 * @since 2023/4/23 21:44
 */
public class FutureThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //3个任务 多个线程
        //创建一个线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        long startTime = System.currentTimeMillis();
        FutureTask<String> futureTask1 = new FutureTask<>(() -> {
            try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
            return "task1 over";});
        threadPool.submit(futureTask1);
        FutureTask<String> futureTask2= new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task2 over";
        });
        threadPool.submit(futureTask2);
        FutureTask<String> futureTask3 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task3 over";
        });
        threadPool.submit(futureTask3);
        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        System.out.println(futureTask3.get());
        long endTime = System.currentTimeMillis();
        System.out.println("==>cost time" + (endTime - startTime) + "毫秒");
        threadPool.shutdown();
    }
    private static void m1() {
        //3个任务 一个线程
        long startTime = System.currentTimeMillis();
        try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
        try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {throw new RuntimeException(e);}
        try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {throw new RuntimeException(e);}
        long endTime = System.currentTimeMillis();
        System.out.println("==>cost time" + (endTime - startTime) + "毫秒");
        System.out.println(Thread.currentThread().getName() + "\t==>end");
    }
}