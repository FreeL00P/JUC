package com.freeloop.juc.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFutureFastDemo
 *
 * @author fj
 * @since 2023/4/24 14:37
 */
public class CompletableFutureFastDemo {
    public static void main(String[] args) {
        CompletableFuture<String> playA = CompletableFuture.supplyAsync(() -> {
            System.out.println("A Come in");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Play A";
        });
        CompletableFuture<String> playB = CompletableFuture.supplyAsync(() -> {
            System.out.println("B Come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Play B";
        });
        //谁快用谁
        /**
         * A Come in
         * B Come in
         * main	=====
         * Play Ais WINNER
         */
        CompletableFuture<String> result = playA.applyToEither(playB, f -> {
            return f + " is WINNER";
        });
        System.out.println(Thread.currentThread().getName() + "\t"+"=====");
        System.out.println(result.join());
    }
}
