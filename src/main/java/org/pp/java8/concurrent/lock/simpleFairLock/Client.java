package org.pp.java8.concurrent.lock.simpleFairLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client {
    private static volatile boolean stop = false;
    private static FairLock lock = new FairLock();

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            while (!stop) {
//                System.out.println(Thread.currentThread().getName() + "启动");
                testFairLock();
            }
        };

        Thread[] threadArray = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threadArray[i] = new Thread(runnable);
        }
        for (int i = 0; i < 10; i++) {
            threadArray[i].start();
        }
        TimeUnit.MILLISECONDS.sleep(1000); // 1秒钟后关闭所有线程的调度
        stop = true;
    }

    public static void testFairLock() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "获得了锁");
            TimeUnit.MILLISECONDS.sleep(100); // 休眠 等其他线程重入
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
//            System.out.println(Thread.currentThread().getName() + "释放了锁");
        }
    }
}
