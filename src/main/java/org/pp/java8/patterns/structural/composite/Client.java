package org.pp.java8.patterns.structural.composite;

/**
 * 提供一个公共接口，来管理整体和局部的关系
 * 组合模式使得用户对单个对象和组合对象的使用具有一致性,它们对公共接口的功能提供实现
 * 比如都可以调用requestFocus();...
 */
public class Client {
    public static void main(String[] args) {
        new PContainer()
                .add(new PLeaf("leaf1"))
                .add(new PLeaf("leaf2"))
                .add(new PLeaf("leaf3"))
                .printSelf();
    }
}
