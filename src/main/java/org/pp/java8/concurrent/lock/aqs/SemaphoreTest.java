package org.pp.java8.concurrent.lock.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore 用于控制并发线程数量，特别是公用资源有限的场景 比如数据库连接池
 */
public class SemaphoreTest {
    private static final int DB_CPU = Runtime.getRuntime().availableProcessors() << 1;
    private static final ExecutorService service = Executors.newFixedThreadPool(DB_CPU);

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(3); // 并发执行许可
        for (int i = 0; i < DB_CPU; i++) {
            service.execute(() -> {
                String name = Thread.currentThread().getName();
                try {
                    /**
                     * 从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，或者线程被中断。
                     * */
                    semaphore.acquire();
//                    semaphore.acquireUninterruptibly();
//                    semaphore.tryAcquire();
                    System.out.println(name + " save data...");
                    TimeUnit.SECONDS.sleep(2);
                    if(semaphore.availablePermits() == 0) {
                        System.out.println(name + "*************************************");
                    }
                    semaphore.release(); // 释放许可，被在等待获得许可的线程拿到
                    if(semaphore.availablePermits() == 0) {
                        System.out.println(name + "release permits, no permits.");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        TimeUnit.SECONDS.sleep(1);
        if(semaphore.availablePermits() == 0) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        service.shutdown();
    }
}
