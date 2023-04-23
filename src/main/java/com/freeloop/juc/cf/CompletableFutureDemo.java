package com.freeloop.juc.cf;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * CompletableFutureDemo
 *
 * @author fj
 * @since 2023/4/23 21:23
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new MyThread2());
        new Thread(futureTask).start();
        //获取futureTask返回值
        System.out.println(futureTask.get());
    }
}
class MyThread implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello RUN");
    }
}
class MyThread2 implements Callable<String>{
    @Override
    public String call() throws Exception {
        System.out.println("Hello CALL");
        return "CALL";
    }
}
