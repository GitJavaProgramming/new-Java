package org.pp.java8.lang;

public class Singleton {
    private volatile static Singleton instance = new Singleton();

    private Singleton() {
        if (instance != this) {
            throw new IllegalStateException("单例破坏！");
        }
    }

    public static synchronized Singleton getInstance() {
        return instance;
    }
}
