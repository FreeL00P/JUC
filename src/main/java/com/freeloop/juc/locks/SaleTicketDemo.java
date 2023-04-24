package com.freeloop.juc.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * SaleTicketDemo
 *
 * @author fj
 * @since 2023/4/24 21:20
 */
class Ticket{
    private int number=50;
    ReentrantLock lock= new ReentrantLock(true);//可重入锁 true设置为公平锁
    public void sale(){
        lock.lock();
        try{
            if(number>0){
                System.out.println(Thread.currentThread().getName()
                +"卖出第：\t"+(number--)+"\t 还剩下"+number);
            }
        }finally {
            lock.unlock();
        }
    }
}
public class SaleTicketDemo {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(()->{for (int i = 0; i <55 ; i++) {ticket.sale();}},"A").start();
        new Thread(()->{for (int i = 0; i <55 ; i++) {ticket.sale();}},"B").start();
        new Thread(()->{for (int i = 0; i <55 ; i++) {ticket.sale();}},"C").start();
    }
}
