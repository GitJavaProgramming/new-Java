package org.pp.java8.patterns.behavioral.template;

/**
 * 模板方法模式与策略关注点不同，策略模式更关注策略的选择
 * 模板方法将公共行为抽象出来，在调用处提供统一调用入口，更关注行为的选择
 * 可以说模板方法模式包含了策略模式，多态特性：因为对象对相同行为的不同外部表现只能通过不同实例体现（方法重载不算！）
 */
public class Client {
    public static void main(String[] args) {
        ICallEntry entry = new ConcreteInstance();
        call(entry);
        entry = new ConcreteObject();
        call(entry);
    }

    public static void call(ICallEntry callEntry) {
        callEntry.defaultMethod();
    }
}
