package org.pp.java8.concurrent.lock;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 *  公平锁在锁释放后会严格按照等待队列去取后续值.非公平锁在实现的时候多次强调随机抢占,与公平锁的区别在于新晋获取锁的进程会有
 *  多次机会去抢占锁。如果被加入了等待队列后则跟公平锁没有区别。
 */
public class ReentrantLockTest {
    private ReentrantLock lock = new ReentrantLock(true);

    /**
     * 测试公平锁：既不能保证加锁顺序也不能保证线程执行顺序，但是能保证所有线程都能获取到锁，因为严格按照等待队列取值
     */
    public void testFairLock() {
        try {
            lock.lock();
            holdLockCount.getAndIncrement();
            map.put(Thread.currentThread().getName(), holdLockCount);
            System.out.println(Thread.currentThread().getName() + "获得了锁");
            TimeUnit.MILLISECONDS.sleep(100); // 休眠1秒 等其他线程重入
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
//            System.out.println(Thread.currentThread().getName() + "释放了锁");
        }
    }

    public static void test1() {
        ReentrantLockTest lockTest = new ReentrantLockTest();
        Runnable runnable = () -> {
//            System.out.println(Thread.currentThread().getName() + "启动");
            lockTest.testFairLock();
        };
        Thread[] threadArray = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threadArray[i] = new Thread(runnable);
        }
//        Arrays.stream(threadArray)/*.parallel()*/.forEach(Thread::start);

//        for (int i = 0; i < 10; i++) {
//            threadArray[i].start();
//        }

        /** 线程池中10个线程都能都获取到锁 */
        ExecutorService service = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 10; i++) {
            service.execute(runnable);
        }
        service.shutdown();
    }

    /**********************************************非公平锁*****************************************************/
    private ReentrantLock nonFairlock = new ReentrantLock();
    private static volatile boolean stop = false;
    /* 线程获得锁的次数 */
    private static AtomicInteger holdLockCount = new AtomicInteger(0);
    /* 每个线程获得锁的次数映射 */
    private static final Map<String, AtomicInteger> map = new ConcurrentHashMap<>(10);
    /**
     * 非公平锁：随机抢占锁，不能保证所有线程都能获得锁？？
     *      * 避免饿死就应该是采用队列的方式，保证每个人都有机会获得请求的资源。
     *      * 当然实现方式可以很多个变化，比如优先级，时间片，等，都是“队列”的特殊形式
     *      * 如何写一个线程饥饿的例子？？ 10个线程竞争，8个线程可以获得锁，2个饥饿。
     */
    public void testNonFairLock() {
        try {
            nonFairlock.lock();
            holdLockCount.getAndIncrement();
            map.put(Thread.currentThread().getName(), holdLockCount);
            System.out.println(Thread.currentThread().getName() + "获得了锁");
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            nonFairlock.unlock();
        }
    }

    public static void test2() throws InterruptedException {
        ReentrantLockTest lockTest = new ReentrantLockTest();
        Runnable runnable = () -> {
            while(!stop) {
//                System.out.println(Thread.currentThread().getName() + "启动");
                lockTest.testNonFairLock();   // 线程饥饿
//                lockTest.testFairLock();   // 所有线程获得锁的次数一样
            }
        };

//        // 测试1
//        ExecutorService service = Executors.newFixedThreadPool(10); // 10个线程
//        service.execute(runnable);
//        service.shutdown();

        // 测试2
        Thread[] threadArray = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threadArray[i] = new Thread(runnable);
        }
        for (int i = 0; i < 10; i++) {
            threadArray[i].start();
        }
        TimeUnit.MILLISECONDS.sleep(1000); // 1秒钟后关闭所有线程的调度
        stop = true;
//        TimeUnit.MILLISECONDS.sleep(1000); // 1秒钟时间内其他饥饿线程将会获得锁
        map.entrySet().parallelStream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(System.out::println);
//        Arrays.stream(threadArray)/*.parallel()*/.forEach(Thread::start);
    }

    private static void shutDown() {
        stop = true;
    }

    static class TestThread extends Thread {

//        public TestThread(CountDownLatch latch) {
//
//        }

        @Override
        public void run() {
            try {
                test3();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void testLock(ReentrantLock lock) {
        try {
            lock.lock();
            holdLockCount.getAndIncrement();
            map.put(Thread.currentThread().getName(), holdLockCount);
            System.out.println(Thread.currentThread().getName() + "获得了锁");
            TimeUnit.MILLISECONDS.sleep(100); // 休眠1秒 等其他线程重入
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
//            System.out.println(Thread.currentThread().getName() + "释放了锁");
        }
    }

    public static void test3() throws InterruptedException {
//        final ReentrantLock lock = new ReentrantLock(true);
        final ReentrantLock lock = new ReentrantLock();
        Runnable runnable = () -> {
            while(!stop) {
                testLock(lock);
            }
        };

//        // 测试1
//        ExecutorService service = Executors.newFixedThreadPool(10); // 10个线程
//        service.execute(runnable); // 一个线程在调度
//        service.shutdown();

        // 测试2
        Thread[] threadArray = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threadArray[i] = new Thread(runnable);
        }

//        for (int i = 0; i < 10; i++) {
//            threadArray[i].start();
//        }

        // 测试3
        Arrays.stream(threadArray)/*.parallel()*/.forEach(Thread::start);

//        TimeUnit.MILLISECONDS.sleep(1000); // 1秒钟后关闭
//        shutDown();
    }

    public static void main(String[] args) throws InterruptedException {
//        test1();
//        test2();

        Thread thread = new TestThread();
        thread.start();
        TimeUnit.SECONDS.sleep(1); // 让任务执行完
        shutDown();
//        TimeUnit.SECONDS.sleep(1); // 饥饿线程获得锁
        map.entrySet().parallelStream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(System.out::println);

    }
}

