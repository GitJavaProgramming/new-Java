package org.pp.patterns.structural.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JdkDynamicProxy {

    public static <T> T proxy(Class<T> proxied, InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class<?>[]{proxied}, handler);
    }

    public static <T> T proxy(Class<T> proxied) {
        return proxy(proxied, JdkDynamicProxyHandler.handlerInstance());
    }
}
