package com.freeloop.juc.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * VolatileNoAtomicDemo
 *
 * @author fj
 * @since 2023/5/3 18:39
 */
class MyNumber{
    volatile int number;
    public void addPlusPlus(){
        number++;
    }
}
public class VolatileNoAtomicDemo {
    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();
        for (int i = 0; i <10;i++){
            new Thread(() -> {
                for (int j =1;j<=1000;j++){
                    myNumber.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }
        TimeUnit.SECONDS.sleep(2);
        System.out.println(myNumber.number);
    }
}
