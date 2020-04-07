package org.pp.java8.patterns.structural.bridge;

/**
 * 接口和行为的实现分离，然后独立的演化
 * 继承机制将接口与行为的实现固定在一起，使得难以对抽象部分和实现部分独立地进行修改、扩充和重用。
 */
public class Client {
    public static void main(String[] args) {
        // 接口的实现ConcreteAction、NewConcreteAction
        Abstraction action = new ConcreteAction();
        doAction(action);

        // 不同行为的实现
        action = new NewConcreteAction(new ConcreteImplementor()/*1*/);
        doAction(action);
        action = new NewConcreteAction(new NewConcreteImplementor()/*2*/);
        doAction(action);
    }

    public static void doAction(Action action/*面向接口*/) {
        action.doAction();
    }
}
