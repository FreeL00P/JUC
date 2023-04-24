package com.freeloop.juc.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * CompletableFutureAPIDemo
 *
 * @author fj
 * @since 2023/4/24 13:23
 */
public class CompletableFutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "abc";
        });
        //超时爆异常
        //completableFuture.get(2L, TimeUnit.SECONDS);
        //超时返回默认值
        //completableFuture.getNow("zxx");
        //暂停几秒钟线程
        TimeUnit.SECONDS.sleep(2);
        //打断获取过程直接返回指定值
        System.out.println(completableFuture.complete("completeValue")+"\t"+completableFuture.join());

    }
}
