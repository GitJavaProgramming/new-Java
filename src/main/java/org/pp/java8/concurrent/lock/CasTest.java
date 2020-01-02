package org.pp.java8.concurrent.lock;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 实现一个线程安全的计数器和一个非线程安全的计数器
 * CAS自旋操作 比较并交换 乐观锁机制
 */
public class CasTest {

    /**
     * 并发线程数
     */
    private static final int THREAD_NUM = 4;

    public static void main(String[] args) throws InterruptedException {
//        Runnable r = new Counter()/*Counter::new*/;
        Counter counter = new Counter();

        List<Thread> threads = Stream.of(new Thread(counter), new Thread(counter), new Thread(counter), new Thread(counter))
                .collect(Collectors.toList());
//        threads.forEach(Thread::start);
        threads.parallelStream().forEach(thread -> {
            thread.start();
            try { // try不能放到外部？？
                thread.join();
            } catch (InterruptedException e) {
//                e.printStackTrace();
                System.out.println("join interrupted.");
            }
        });
        System.out.println("atomicInteger = " + counter.atomicInteger.get());
        System.out.println("anInt = " + counter.anInt);
    }

    static class Counter implements Runnable {

        private /*static*/ AtomicInteger atomicInteger = new AtomicInteger(0);
        private /*static*/ int anInt = 0;
        /**
         * 计数次数
         */
//        private volatile int countNum = 1000000; // ++操作非原子操作，非线程安全
        private AtomicInteger countNum = new AtomicInteger(1000000);


        @Override
        public void run() {
            while (countNum.getAndDecrement() > 0) {
                safeCount();
                notSafeCount();
            }
        }


        public void safeCount() {
            while (true) {
                int anInt = atomicInteger.get(); // volatile value
                boolean cmpSuccess = atomicInteger.compareAndSet(anInt, ++anInt);
                if (cmpSuccess) {
                    break;
                }
            }
        }

        public void notSafeCount() {
            anInt++;
        }
    }
}
