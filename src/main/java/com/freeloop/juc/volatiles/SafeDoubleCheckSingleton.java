package com.freeloop.juc.volatiles;

/**
 * SafeDoubleCheckSingleton
 *
 * @author fj
 * @since 2023/5/3 20:00
 */
public class SafeDoubleCheckSingleton {
    //通过volatile声明，实现线程安全的延迟初始化
    private volatile static SafeDoubleCheckSingleton singleton;
    //构造器私有
    private SafeDoubleCheckSingleton(){
    }
    public static SafeDoubleCheckSingleton getInstance(){
        //双重检查锁
        if (singleton ==null){
            //多线程并发创建对象是，会通过加速保证只有一个线程能够创建对象
            synchronized (SafeDoubleCheckSingleton.class){
                if (singleton==null){
                    //隐患：多线程环境下，由于重排序，该对象可能还没有创建完成就被其他线程读取
                    //解决隐患原理:利用volatile，禁止"初始化对象（2）"和"设置singleton指向内存空间（3）"的重排序
                    singleton=new SafeDoubleCheckSingleton();
                }
            }
        }
        //对象创建完毕，执行getInstance()将不需要获取锁，直接返回创建对象
        return singleton;
    }
}
