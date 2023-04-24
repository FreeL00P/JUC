package com.freeloop.juc.cf;

import java.util.concurrent.CompletableFuture;

/**
 * ComletableFutureAPI3Demo
 *
 * @author fj
 * @since 2023/4/24 13:59
 */
public class CompletableFutureAPI3Demo {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(()-> 1)
                .thenApply(f-> f+2)
                .thenApply(f-> f+3)
                .thenAccept(System.out::println);//消费型函数式接口
        System.out.println("<== ==>");
        //thenRun 任务A执行完执行任务B不需要A的返回结果
        System.out.println(CompletableFuture.supplyAsync(()->"ResultA").thenRun(()->{}).join());
        System.out.println("<== ==>");
        //thenRun 任务A执行完执行任务B 需要A的返回结果
        System.out.println(CompletableFuture.supplyAsync(()->"ResultA").thenAccept(System.out::println).join());
    }
}
