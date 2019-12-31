package org.pp.java8.concurrent.util;

import java.util.concurrent.TimeUnit;

/**
 * 两个线程wait notify，前者检查flag是否为false，如果符合要求则进行后续操作,否则在监视器锁上面等待
 * 后者在睡眠了一段时间后对监视器锁进行通知
 */
public class ThreadCommunicationTest {

    private static volatile boolean flag = false;

    public static void main(String[] args) {
        Object objLock = new Object();
        new WaitThread(objLock).start();
        new NotifyThread(objLock).start();
    }

    static class WaitThread extends Thread {

        private Object objLock;

        public WaitThread(Object objLock) {
            this.objLock = objLock;
        }

        @Override
        public void run() {
            synchronized (objLock) {
                if (!flag) {
                    System.out.println("release lock and waiting to hold lock.");
                    try {
                        objLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("hold lock and run here.");
            }
        }
    }

    static class NotifyThread extends Thread {

        private Object objLock;

        public NotifyThread(Object objLock) {
            this.objLock = objLock;
        }

        @Override
        public void run() {
            synchronized (objLock) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("唤醒在对象监视器锁上等待的一个线程！");
                objLock.notify(); // 唤醒所有在监视器上等待的线程
            }
        }
    }
}
