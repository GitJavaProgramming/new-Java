package org.pp.java8.concurrent.demo.lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * N个线程，打印AaBb...YyZz，M遍
 */
public class FirstReentrantLock {

    private final int M = 2; // 打印遍数
    private final int threadNum; // 线程数
    private static final Lock lock = new ReentrantLock();

    private BlockingQueue<Condition> conditionQueue = null;
    private BlockingQueue<Condition> wakeUpQueue = null;
    private Semaphore[] semaphores = null;

    private final String str = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

    public FirstReentrantLock(int threadNum) {
        this.threadNum = threadNum;
        this.conditionQueue = new ArrayBlockingQueue(threadNum);
        this.wakeUpQueue = new ArrayBlockingQueue(threadNum);
        this.semaphores = new Semaphore[threadNum];
        try {
            for (int i = 0; i < threadNum; i++) {
                this.conditionQueue.put(lock.newCondition());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitLock() {
        String name = Thread.currentThread().getName();
        System.out.println("conditionQueue.size = " + conditionQueue.size());
        Condition condition = null;
        while ((condition = conditionQueue.poll()) != null) {
            lock.lock();
            try {
                wakeUpQueue.put(condition);
                System.out.println("free Lock and wait, node : " + condition);
                System.out.println(condition + " is awaiting...");
                condition.await();
                int nameInt = Integer.valueOf(name);
                int index = nameInt % threadNum;
                System.out.println(name + "-" + str.charAt(index));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void wakeUp() {
        System.out.println("current conditionQueue.size() = " + conditionQueue.size());
        lock.lock();
        try {
            Condition node = null;
            while ((node = wakeUpQueue.poll()) != null) {
                System.out.println("wakeUp node : " + node);
                System.out.println(node + " is signalAll...");
                node.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    /*
    private static final char[] chars;
    static {
        chars = new char[52];
        int t1 = Character.valueOf('A');
        int t2 = Character.valueOf('a');
        int t3 = Character.valueOf('Z');
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
        for (int i = 'A'; i <= 'z'; i++) {
            if (i <= 'Z') {
                chars[i - t1] = (char) i;
            } else if (i >= 'a' && i <= 'z') {
//                System.out.println(i);
                chars[i - t2 + t3 - t1] = (char) i;
            }
        }
        System.out.println(chars);
    }
*/

}
