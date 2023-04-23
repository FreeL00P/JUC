package com.freeloop.juc.cf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * FutureAPIDemo
 *
 * @author fj
 * @since 2023/4/23 22:28
 */

/**
 * get 容易导致阻塞，一般建议放在程序后面，一旦调用就必须等到结果才会往下执行
 */
public class FutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "---come in");
            //暂停几秒钟
            TimeUnit.SECONDS.sleep(5);
            return "task over";
        });
        Thread t1 = new Thread(futureTask);
        t1.start();
        System.out.println(Thread.currentThread().getName() + "忙其他任务");
        //System.out.println(futureTask.get());
       //设置超时时间 超时强行中断程序，抛出异常
       // System.out.println(futureTask.get(3,TimeUnit.SECONDS));
        //设置轮询 每次都需要判断任务是否完成 容易造成cpu空转影响性能
        //判断任务是否完成
        while (true){
            if (futureTask.isDone()){
                System.out.println(futureTask.get());
                break;
            }else {
                System.out.println("futureTask没有完成");
                //暂停几秒钟
                TimeUnit.MILLISECONDS.sleep(500);
            }
        }
    }
}
