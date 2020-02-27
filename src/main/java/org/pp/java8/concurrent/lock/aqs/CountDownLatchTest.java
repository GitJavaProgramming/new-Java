package org.pp.java8.concurrent.lock.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * CountDownLatch---内部共享锁
 * 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 * CountDownLatch 的一个有用特性是，它不要求调用 countDown 方法的线程等到计数到达零时才继续，而在所有线程都能通过之前，
 * 它只是阻止任何线程继续通过一个 await。
 * 例如，应用程序的主线程希望在负责启动框架服务的线程已经启动所有的框架服务之后再执行。
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3/*用给定的计数初始化CountDownLatch*/);
//        ThreadGroup threadGroup = new ThreadGroup("biz-group");
//        threadGroup.activeCount();
        AThread a1 = new AThread(latch);
        AThread a2 = new AThread(latch);
        AThread a3 = new AThread(latch);

        Stream.of(a1, a2, a3).parallel().forEach(t -> t.start());

        System.out.println(a1.getName() + " -- " + a1.getState());
        latch.await(); // 使当前线程在锁存器倒计数至零之前一直等待，除非线程被中断。
        System.out.println(a1.getName() + " -- " + a1.getState());
        if (a1.isAlive()) {
            System.out.println("a1 is alive...");
        }
    }

    static class AThread extends Thread {

        private final CountDownLatch latch;

        public AThread(CountDownLatch latch) {
            this.latch = latch;
        }

        public AThread(ThreadGroup group, String name, CountDownLatch latch) {
            super(group, name);
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("sleep interrupted.");
            }
            latch.countDown(); // 递减锁存器的计数，如果计数到达零，则释放所有等待的线程。
            try {
                TimeUnit.SECONDS.sleep(3); // 长时间任务 使线程保持非终止状态
            } catch (InterruptedException e) {
                System.out.println("sleep interrupted.");
            }
        }
    }
}
