package com.freeloop.juc.threadLocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ThreadLocalDemo
 *
 * @author fj
 * @since 2023/5/6 22:47
 */
/*
需求1 5个销售卖房子，集团高层只关心销售总量的准确统计数
需求2 5个销售卖完随机数房子，各自独立销售额度，自己业绩按提成走，
 */
class House{
    int saleCount=0;
    public synchronized void saleHouse(){
        ++saleCount;
    }
   /* ThreadLocal<Integer> saleVolume=new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };*/
   ThreadLocal<Integer> saleVolume=ThreadLocal.withInitial(()->0);
    public void saleVolumeByThreadLocal(){
        saleVolume.set(1+saleVolume.get());
    }
}

public class ThreadLocalDemo {
    public static void main(String[] args) {
        House house = new House();
        for (int i = 1; i <=5; i++) {
            new Thread(()->{
                int size = new Random().nextInt(5) + 1;
                for (int j = 1; j <=size ; j++) {
                    house.saleHouse();
                    house.saleVolumeByThreadLocal();
                }
                System.out.println(Thread.currentThread().getName()+"\t"+"卖出多少套："+house.saleVolume.get());
            },String.valueOf(i)).start();
        }
        try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println(Thread.currentThread().getName()+"\t"+"共计卖出多少套："+house.saleCount);
    }

}
