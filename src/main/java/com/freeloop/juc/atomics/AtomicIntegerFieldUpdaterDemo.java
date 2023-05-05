package com.freeloop.juc.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFieldUpdaterDemo
 *
 * @author fj
 * @since 2023/5/5 18:30
 */
class BankAccount{
    String bankName="BBC";
    //更新的对象属性必须使用public volatile修饰
    public volatile int money=0;
    public  void addMoney(){
        money++;
    }
    //因为对象的属性修改类型原子类都是抽象类，所以每次1使用都必须
    //使用静态方法newUpdater()新建一个更新类，并且需要设置想要更新的类和属性
    AtomicIntegerFieldUpdater<BankAccount> fieldUpdater=
            AtomicIntegerFieldUpdater.newUpdater(BankAccount.class,"money");
    //不加synchronized保证高性能原子性
    public void transMoney(BankAccount  bankAccount){
        fieldUpdater.getAndIncrement(bankAccount);
    }
}

/*
 * 一一种线程安全方式操作费线程安全对象的某些字段
 * 使用AtomicIntegerFieldUpdater实现
 */
public class AtomicIntegerFieldUpdaterDemo {
    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
               try {
                   for (int j = 1; j <=1000 ; j++) {
                       //bankAccount.addMoney();
                       bankAccount.transMoney(bankAccount);
                   }
               }finally {
                   countDownLatch.countDown();
               }
            },String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t"
                    +"==>result:"+bankAccount.money);
    }
}
