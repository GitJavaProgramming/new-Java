package org.pp.java8.lang.singleton;

public class Singleton3 {
    private volatile static Singleton3 instance = null;
    private volatile static boolean flag = true;

    private Singleton3() {
        if (!flag) { // 延迟初始化的危害，不知道是否已经被实例化
            throw new IllegalStateException("单例破坏！");
        }
    }

    public static Singleton3 getInstance() {
        if (instance == null) { // 判断是否已经实例化
            synchronized (Singleton3.class) { // 多线程时，只有一个线程可以获取监视器锁
                if (instance == null) {
                    instance = new Singleton3();
                    flag = false; // 单例标志位
                }
            }
        }
        return instance;
    }
}
