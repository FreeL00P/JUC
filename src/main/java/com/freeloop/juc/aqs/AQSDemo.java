package com.freeloop.juc.aqs;

import java.util.concurrent.locks.ReentrantLock;

/**
 * AQSDemo
 *
 * @author fj
 * @since 2023/5/11 18:34
 */
public class AQSDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(false);
        lock.lock();
        try {

        }finally {
            lock.unlock();
        }
    }
}
