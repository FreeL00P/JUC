package com.freeloop.juc.cf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFutureMallDemo
 *
 * @author fj
 * @since 2023/4/24 11:16
 */
public class CompletableFutureMallDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "Hello 1234";
        });
        //get 在编译时会爆出异常 而join不会 两则执行结果没有区别
        System.out.println(completableFuture.get());
        System.out.println(completableFuture.join());

    }
}


@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true) //链式调用
class Student{
    private Integer id;
    private String studentName;
    private String major;
}