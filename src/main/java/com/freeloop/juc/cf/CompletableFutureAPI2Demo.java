package com.freeloop.juc.cf;

import java.util.concurrent.*;

/**
 * CompletableFutureAPI2Demo
 *
 * @author fj
 * @since 2023/4/24 13:40
 */
public class CompletableFutureAPI2Demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("111");
            return 1;
        },threadPool).thenApply(f->{
            System.out.println("222");
            return f+2;
        }).thenApply(f->{
            int i =2/0;
            System.out.println("333");
            return f+3;
        }).handle((f,e)->{
            //有异常继续往下执行
            System.out.println("上面报错了");
            System.out.println("333");
            return f+3;
        }).whenComplete((v,e) -> {
            if (e==null) {
                System.out.println("ret==>"+v);
            }
        }).exceptionally(e->{
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        });
        System.out.println(Thread.currentThread().getName()+"==>RUN");
        threadPool.shutdown();
    }
}
