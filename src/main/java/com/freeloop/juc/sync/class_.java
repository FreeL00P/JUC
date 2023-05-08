package com.freeloop.juc.sync;

import org.openjdk.jol.info.ClassLayout;

/**
 * class_
 *
 * @author fj
 * @since 2023/5/8 22:14
 */
class T{
    Integer age;
    Integer age1;
}
public class class_ {
    public static void main(String[] args) {
//        T o = new T();
//        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println("================================================");

        T o = new T();
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
