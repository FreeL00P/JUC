package com.freeloop.juc.cf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * CompletableFutureMallDemo
 *
 * @author fj
 * @since 2023/4/24 11:16
 */
public class CompletableFutureMallDemo {
    static List<NetMall> lists= Arrays.asList(
            new NetMall("jd"),
            new NetMall("tb"),
            new NetMall("pdd")
    );

    /**
     * step by step
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPriceByStep(List<NetMall> list,String productName) {
        List<String> stringList = list.stream().map(netMall -> String.format(
                productName + " in %s price is %.2f",
                netMall.getNetMallName(), netMall.calPrice(productName))).toList();
        return stringList;
    }
    public static List<String> getPriceByCompletableFuture(List<NetMall> list,String productName) {
        return list.stream().map(
                netMall -> CompletableFuture.supplyAsync(()->
                String.format(productName + " in %s price is %.2f",
                netMall.getNetMallName(),
                netMall.calPrice(productName))))
                .toList()
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        List<String> price = getPriceByStep(lists, "mysql");
        long endTime = System.currentTimeMillis();
        System.out.println("step by step 消耗时间==>"+(endTime-startTime));
        System.out.println("price = " + price);

        long startTime2 = System.currentTimeMillis();
        List<String> price2 = getPriceByCompletableFuture(lists, "mysql");
        long endTime2 = System.currentTimeMillis();
        System.out.println("异步 消耗时间==>"+(endTime2-startTime2));
        System.out.println("price = " + price2);
    }
}


class NetMall{
    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }
    public double calPrice(String productName)  {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0) + productName.charAt(0);
    }
}