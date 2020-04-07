package org.pp.java8.patterns.structural.proxy;

import java.util.List;

/**
 * 代理，拥有一个对象的所有（开放/可访问）权限：数据、构造、行为，同时可以改造这些权限
 * jdk动态代理：动态行为代理，运用多态，生成代理类进行方法分派
 * cglib：修改字节码，覆盖原始文件
 */
public class Client {
    public static void main(String[] args) {
        new ThreadProxy("thread").printSelfName().start();

        List list = JdkDynamicProxy.proxy(List.class);
        list.clear(); // return void
//        Integer integer = JdkDynamicProxy.proxy(Integer.class);
//        integer.notify();  // java.lang.Integer is not an interface
    }
}
