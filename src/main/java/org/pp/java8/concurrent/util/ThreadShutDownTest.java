package org.pp.java8.concurrent.util;

import java.util.concurrent.TimeUnit;

/**
 * 主线程（控制）停止一个不断进行变量累加的线程
 */
public class ThreadShutDownTest {
    public static void main(String[] args) throws InterruptedException {
        CounterThread counterThread = new CounterThread();
        counterThread.start();
        TimeUnit.SECONDS.sleep(1);
        counterThread.interrupt(); // 中断线程，捕获抛出异常可以处理，如果没有可中断的阻塞方法则设置中断位
//        counterThread.shutDown();
    }

    static class CounterThread extends Thread {
        private int count = 0;
        /* volatile确保内存可见性，立即响应 */
        private volatile boolean canStop = false;

        @Override
        public void run() {
            while (!canStop && !Thread.currentThread().isInterrupted()/*中断阻塞方法会清除中断状态(以不影响其他代码执行),捕获抛出中断异常，进行中断处理*/) {
                count++;
                try { // try放在最外层（while语句之外）可以确保必须响应中断
                    TimeUnit.MILLISECONDS.sleep(100); // 中断发生时，代码可能没有执行到这里
                } catch (InterruptedException e) {
                    System.out.println("线程被中断！");
//                    Thread.interrupted(); // 清除中断状态
                    shutDown();
                }
            }
            System.out.println("count = " + count);
        }

        public void shutDown() {
            canStop = true;
        }
    }
}
