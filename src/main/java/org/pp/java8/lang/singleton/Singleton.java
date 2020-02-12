package org.pp.java8.lang.singleton;

public class Singleton implements SingletonType {
    private transient/*字段不能被序列化*/ volatile static Singleton instance = new Singleton();

    private Singleton() {
        if (instance != this) {
            throw new IllegalStateException("单例破坏！"); // 在clone之前
        }
    }

    public static synchronized Singleton getInstance() {
        return instance;
    }

    public Singleton cloneObj() {
        Singleton singleton = null;
        try {
            singleton = (Singleton) clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return singleton;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("不支持克隆.");
    }
}
