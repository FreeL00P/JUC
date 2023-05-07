package com.freeloop.juc.threadLocal;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ReferenceDemo
 *
 * @author fj
 * @since 2023/5/7 12:51
 */
class MyObject{
    @Override
    protected void finalize() throws Throwable {
        //finalize的通常目的是在对象被不可撤销地丢弃之前执行清理操作
        System.out.println("====>invoke finalize method");
    }
}
public class ReferenceDemo {
    public static void main(String[] args) {
        phantomReference();
    }


    private static void phantomReference() {
        MyObject myObject = new MyObject();
        //引用队列
        ReferenceQueue<MyObject> referenceQueue=new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference=new PhantomReference<>(myObject,referenceQueue);
        //System.out.println(phantomReference.get());
        List<byte[]> list=new ArrayList<>();
        new Thread(()->{
            while (true) {
                list.add(new byte[1024 * 1024]);
                try {TimeUnit.MILLISECONDS.sleep(250);} catch (InterruptedException e) {throw new RuntimeException(e);}
                //返回此引用对象的引用对象。由于幻像引用的引用始终不可访问，因此此方法始终返回 null。
                System.out.println(phantomReference.get()+"\t"+"list add ok");
            }
        },"t1").start();
        new Thread(()->{
            while (true) {
                Reference<? extends MyObject> reference = referenceQueue.poll();
                if (reference!=null){
                    //说明有虚对象加入到了队列
                    System.out.println("==>有虚对象回收加入到了队列");
                    break;
                }
            }
        },"t2").start();
    }

    private static void weakReference() {
        //弱引用
        WeakReference<MyObject> weakReference=new WeakReference<>(new MyObject());
        System.out.println("===>gc before内存够用：\t"+weakReference.get());
        System.gc();//弱引用，只要发生gc,马上就会被回收，无论内存是否够用
        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println("===>gc after内存够用：\t"+weakReference.get());
    }

    private static void softReference() {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
        System.gc();//软引用只有在内存不够时才会回收
        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println("===>gc after内存够用：\t"+softReference.get());
        try{
            byte[] bytes=new byte[20*1024*1024];

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("===>gc after内存不够用：\t"+softReference.get());
        }
    }

    private static void strongReference() {
        MyObject myObject = new MyObject();
        System.out.println("gc before:"+"\t"+myObject);
        myObject=null;
        System.gc();//人工开启gc
        System.out.println("gc after:"+"\t"+myObject);
    }
}
