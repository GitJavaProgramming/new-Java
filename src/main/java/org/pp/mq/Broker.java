package org.pp.mq;

import java.util.concurrent.ArrayBlockingQueue;

public class Broker {

    public static final int MAX_SIZE = 3;

    private static final ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue(MAX_SIZE);

    public static void produce(String msg) {
        if (messageQueue.offer(msg)) {
            System.out.println("成功向处理中心投递消息：" + msg + "，暂存消息数：" + messageQueue.size());
        } else {
            System.out.println("消息队列已满，不能放入消息。");
        }
        System.out.println("=========================================================================");
    }

    public static String consume() {
        String msg = messageQueue.poll();
        if (msg != null) {
            System.out.println("成功消费消息：" + msg + "，剩余消息数：" + messageQueue.size());
        } else {
            System.out.println("消息队列中没有消息。");
        }
        System.out.println("=========================================================================");
        return msg;
    }
}
