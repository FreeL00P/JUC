package com.freeloop.juc.cf;

import java.util.concurrent.*;

/**
 * CompletableFutureBuildDemo
 *
 * @author fj
 * @since 2023/4/24 10:06
 */
public class CompletableFutureBuildDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

       ExecutorService threadPool = Executors.newFixedThreadPool(3);
/*        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "==>Run");
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },threadPool);
        System.out.println( completableFuture.get());*/
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "HELLO"+Thread.currentThread().getName();
        }, threadPool);
        System.out.println(completableFuture.get());
        threadPool.shutdown();
    }

}
