package org.pp.java8.patterns.structural.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkDynamicProxyHandler implements InvocationHandler {

    private void preProcess() {
        System.out.println("preProcess");
    }

    private void postProcess() {
        System.out.println("postProcess");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        preProcess();
//        method.getAnnotation()
        postProcess();
        return null; // 接口不提供实现，需要自己实现方法处理逻辑 代理的接口中方法返回值必须为void,并不进行真正的方法调用
    }

    private static class Holder {
        private static final InvocationHandler instance = new JdkDynamicProxyHandler();
    }

    public static InvocationHandler handlerInstance() {
        return Holder.instance;
    }
}
