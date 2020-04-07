package org.pp.java8.patterns.behavioral.observer;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 观察者模式：要在被观察对象中指定观察者建立对象关联，当被观察对象状态改变时，触发观察者更新事件
 */
public class Client {
    public static void main(String[] args) {
        // 多个观察者
        Observer observer1 = new Observer("observer1");
        Observer observer2 = new Observer("observer2");
        List<Observer> list = Lists.newArrayList(observer1, observer2);

        // 被观察者
        Observable observable = new Observable(list);
        observable.change();
    }
}
