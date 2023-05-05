package com.freeloop.juc.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReferenceDemo
 *
 * @author fj
 * @since 2023/5/5 9:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
class User{
    String username;
    int age;
}
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();
        User zs = new User("zs", 19);
        User ls = new User("lisi", 20);
        atomicReference.set(zs);
        //期望值是zs就修改为ls
        System.out.println(atomicReference.compareAndSet(zs,ls)+"\t"+atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(zs,ls)+"\t"+atomicReference.get().toString());

    }
}
