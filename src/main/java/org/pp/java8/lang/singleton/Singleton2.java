package org.pp.java8.lang.singleton;

public class Singleton2 {

    private Singleton2() {
        if (Holder.instance != this) {
            throw new IllegalStateException("单例破坏！");
        }
    }

    public static Singleton2 getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static Singleton2 instance = new Singleton2();
    }
}
