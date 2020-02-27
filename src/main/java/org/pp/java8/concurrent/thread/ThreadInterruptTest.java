package org.pp.java8.concurrent.thread;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 如果当前线程没有中断它自己（这在任何情况下都是允许的），则该线程的 checkAccess 方法就会被调用，这可能抛出 SecurityException。
 * 如果线程在调用 Object 类的 wait()、wait(long) 或 wait(long, int) 方法，或者该类的 join()、join(long)、join(long, int)、sleep(long)
 * 或 sleep(long, int) 方法过程中受阻，则其中断状态将被清除，它还将收到一个 InterruptedException。
 * 如果该线程在可中断的通道上的 I/O 操作中受阻，则该通道将被关闭，该线程的中断状态将被设置并且该线程将收到一个 ClosedByInterruptException。
 * 如果该线程在一个 Selector 中受阻，则该线程的中断状态将被设置，它将立即从选择操作返回，并可能带有一个非零值，就好像调用了选择器的 wakeup 方法一样。
 * 如果以前的条件都不满足，则将该线程设置为中断。
 * interrupt调用时，可中断方法捕获中断异常之后，为了不影响线程中其他方法执行，会擦除中断标识
 * 查阅方法javadoc
 * public void interrupt()
 * 测试线程是否已经中断。线程的中断状态 不受该方法的影响。
 * public boolean isInterrupted()
 * 触发中断时第一次调用返回true并且清除中断标识，以后调用就是false，除非再次触发中断
 * public static boolean interrupted()
 */
public class ThreadInterruptTest {

    public static void main(String[] args) throws InterruptedException {
        interruptTest();
//        threadInterruptedTest();
//        isInterruptedTest();
    }

    public static void isInterruptedTest() {
        Thread thread3 = new Thread() {

            private volatile boolean closed = false;

            @Override
            public void run() {

                try {
                    while (!closed && !/*Thread.currentThread().*//*注释这里，IDE不会提示让你转换成lambda*/isInterrupted()) { // 测试是否中断
                        // do something
                        TimeUnit.SECONDS.sleep(1);
                    }
                } catch (InterruptedException e) {
                    // 方法执行时，有几次这里没有打印???
                    System.out.println("响应中断。中断标识重置（重新设置为没有被中断）--查看本类说明 isInterrupted ： " + isInterrupted());
                }
                System.out.println("thread3 exited.");
            }
        };
        thread3.start();
        thread3.interrupt();
    }

    public static void threadInterruptedTest() {
        Thread thread2 = new Thread(() -> {
//            System.out.println(this);
            long n1 = 0;
            while (n1++ < Math.sqrt(Long.MAX_VALUE)) {
//                Thread.currentThread().isInterrupted();
                if (Thread.interrupted()) { // 中断时第一次调用返回true并且清除中断标识
                    System.out.println(" ====>> " + Thread.interrupted()); // 以后调用总是false，除非再次触发中断
                }
            }
            System.out.println("finished run.");
        });
        thread2.start();
        System.out.println("thread2 isInterrupted : " + thread2.isInterrupted()); // false
        thread2.interrupt();
        System.out.println("thread2 isInterrupted : " + thread2.isInterrupted()); // true
    }

    public static void interruptTest() throws InterruptedException {
        Runnable r1 = () -> {
            String threadName = Thread.currentThread().getName();
            try {
                System.out.println(threadName + " time before sleep :" + LocalDateTime.now());
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
//                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                System.out.println(threadName + " time was interrupted :" + LocalDateTime.now());
                System.out.println(threadName + " sleep was interrupted.");
            }
        };
        Thread thread = new Thread(r1);
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("thread state:" + thread.getState()); // TIMED_WAITING
        System.out.println("thread isInterrupted : " + thread.isInterrupted()); // false 没被中断
        thread.interrupt(); // 中断thread的等待状态
        System.out.println("thread isInterrupted : " + thread.isInterrupted()); // false 清除中断标识位
        System.out.println("thread.interrupt() thread state:" + thread.getState()); // 任何可能状态catch块处理时间限制
    }

}
