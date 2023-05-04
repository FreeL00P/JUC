package com.freeloop.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * demo01
 *
 * @author fj
 * @since 2023/5/4 22:46
 */
public class AtomicIntegerCAS  {
    public static void main(String[] args) throws InterruptedException {
        // 初始值设为100
        AtomicInteger ai = new AtomicInteger(100);
        // 启动线程1
        Thread t1 = new Thread(() -> {
            // 获取初始值，用于CAS操作
            int exValue = ai.get();
            boolean isSuccess = false;
            while (!isSuccess) {
                int curValue = ai.get();
                // 如果当前值等于初始值，则CAS修改值，设置修改成功标记isSuccess为true
                if (curValue == exValue) {
                    isSuccess = ai.compareAndSet(exValue, exValue + 1);
                } else {
                    // 如果当前值不等于初始值，更新初始值为当前值，继续循环
                    exValue = curValue;
                }
            }
            // 线程1增加后的值
            System.out.println(Thread.currentThread().getName() + "增加后的值：" + ai.get());
        });
        // 启动线程2
        Thread t2 = new Thread(() -> {
            // 获取初始值，用于CAS操作
            int exValue = ai.get();
            boolean isSuccess = false;
            while (!isSuccess) {
                int curValue = ai.get();
                // 如果当前值等于初始值，则CAS修改值，设置修改成功标记isSuccess为true
                if (curValue == exValue) {
                    isSuccess = ai.compareAndSet(exValue, exValue + 1);
                } else {
                    // 如果当前值不等于初始值，更新初始值为当前值，继续循环
                    exValue = curValue;
                }
            }
            // 线程2增加后的值
            System.out.println(Thread.currentThread().getName() + "增加后的值：" + ai.get());
        });
        // 启动两个线程，并等待执行完成
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        // 输出最终结果，AtomicInteger初始值100加上两次增加操作后的值为102
        System.out.println("最终的值为：" + ai.get());
    }
}
