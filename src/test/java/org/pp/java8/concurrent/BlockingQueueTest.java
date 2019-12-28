package org.pp.java8.concurrent;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueTest {

    @Test
    public void ArrayBlockingQueueTestCase() {
        final int MAX_SIZE = 3;
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue(MAX_SIZE);
        arrayBlockingQueue.offer(1);
        arrayBlockingQueue.offer(2);
        arrayBlockingQueue.offer(3);

//        arrayBlockingQueue.add(4);  // throw java.lang.IllegalStateException
        boolean insertState = arrayBlockingQueue.offer(4);
        System.out.println("insertState:" + insertState);
        Integer element = arrayBlockingQueue.peek();    // 返回队列首元素
        System.out.println("peek element:" + element);
        System.out.println("after peek:" + arrayBlockingQueue);
        element = arrayBlockingQueue.poll();
        System.out.println("poll element:" + element);
        System.out.println("after poll:" + arrayBlockingQueue);
        try {
            element = arrayBlockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("take element:" + element);
        System.out.println("after take:" + arrayBlockingQueue);

        // add offer(增加元素，立即返回) peek(不删除元素，返回队首元素) put(阻塞操作) poll(非阻塞 立即返回) take(阻塞等待)
    }
}
