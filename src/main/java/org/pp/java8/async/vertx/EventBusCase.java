package org.pp.java8.async.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

import static org.pp.java8.async.vertx.EventBusCase.Holder.eb;

public class EventBusCase {
    private static Vertx vertx = Vertx.vertx();
    //    private static EventBus eb;
    private static MessageConsumer<?> consumer;

    public static void eventSend(/*Buffer message*/) {
        // 设置发送的消息头
        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("some-header", "some-value"); // 头信息
        eb.send("org.pp.bus.registry", "Yay! Someone kicked a ball", options); // 发送消息
    }

    public static void eventRegisterClient() {
        if (consumer != null) {
            return;
        }
        consumer = eb.consumer("org.pp.bus.registry");
        consumer.handler(message -> System.out.println("I have received a message: " + message.body()));
        // 完成时通知
        consumer.completionHandler(res -> {
            if (res.succeeded()) {
                System.out.println("The handler registration has reached all nodes");
            } else {
                System.out.println("Registration failed!");
            }
        });
    }

    public static void unRegister(MessageConsumer<?> consumer) {
        // 注销
        consumer.unregister(res -> {
            if (res.succeeded()) {
                System.out.println("The handler un-registration has reached all nodes");
            } else {
                System.out.println("Un-registration failed!");
            }
        });
    }

    static class Holder {
        protected static EventBus eb = vertx.eventBus();
    }

    public static void main(String[] args) {
//        MyVerticle verticle = new MyVerticle();
//        Vertx vertx = verticle.getVertx();
//        eb = vertx.eventBus(); // 获取总线

        eventRegisterClient(); // 向总线注册消费者客户端

        eb.publish("org.pp.bus.registry", "Yay! Someone kicked a ball"); // 发布消息
        eventSend(); // 发送消息
        // 1秒后注销
        vertx.setTimer(1000, event -> {
            unRegister(consumer);
            vertx.close();
        });
        // 关闭时注销
//        vertx.close(event -> {
//            if(event.succeeded()) {
//                unRegister(consumer);
//            } else {
//                System.out.println("客户端注销失败");
//            }
//        });
    }
}
