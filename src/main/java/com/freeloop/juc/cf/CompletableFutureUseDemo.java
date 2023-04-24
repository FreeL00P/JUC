package com.freeloop.juc.cf;

import java.util.concurrent.*;

/**
 * CompletableFutureUseDemo
 *
 * @author fj
 * @since 2023/4/24 10:40
 */
public class CompletableFutureUseDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建一个线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try{
            CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "----come in");
                int result = ThreadLocalRandom.current().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("-->一秒钟后出结果==>"+result);
                if (result<2){
                    int i=10/0;
                }
                return result;
                //v 上一步的结果，e 上一步可能发生的异常
            },threadPool).whenComplete((v,e)->{
                if (e == null){
                    System.out.println("计算完成。ret=="+v);
                }
            }).exceptionally(e->{
                e.printStackTrace();
                System.out.println("异常情况"+e.getCause()+"\t"+e.getMessage());
                return null;
            });
            System.out.println(Thread.currentThread().getName()+"线程先去忙其他任务");
            //主线程不要立即结束 ，否则CompletableFuture默认使用的线程会立即关闭
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){

        }finally {
            threadPool.shutdown();
        }

    }
    public static void thread1() throws ExecutionException, InterruptedException{
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "----come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("-->一秒钟后出结果");
            return result;
        });
        System.out.println(Thread.currentThread().getName() + "线程先去做其他事情");
        System.out.println(completableFuture.get());
    }
}
