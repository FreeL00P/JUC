package com.freeloop.juc.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFutureWithThreadPoolDemo
 *
 * @author fj
 * @since 2023/4/24 14:13
 */

/**
 * 没有传入默认线程池 都使用默认线程池ForkJoinPool
 * 传入一个自定义线程池后，第一个任务使用该线程池，则接下来的任务共用一个线程池
 * 调用thenRun方法执行第二个任务时，则第二个任务与第一个任务共用一个线程池
 * 调用thenRunAsync执行第二个任务时，则第一个任务的是你自己传入的线程池，
 *  第二个任务使用的是ForkJoinPool 线程池
 *  Tips：有可能处理的太快，系统优化切换原则，直接使用main线程处理
 *  其他xxx xxxAsync方法同理
 */
public class CompletableFutureWithThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try{
            CompletableFuture.supplyAsync(()->{
                System.out.println("No 1 TASK \t"+Thread.currentThread().getName());
                return "abcd";
            },threadPool).thenRunAsync(()->{
                System.out.println("No 2 TASK \t"+Thread.currentThread().getName());
            }).thenRun(()->{
                System.out.println("No 3 TASK \t"+Thread.currentThread().getName());
            }).thenRun(()->{
                System.out.println("No 4 TASK \t"+Thread.currentThread().getName());
            });
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
        TimeUnit.SECONDS.sleep(3);
    }
}
