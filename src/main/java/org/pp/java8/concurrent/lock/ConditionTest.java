package org.pp.java8.concurrent.lock;

import org.pp.java8.concurrent.annotation.NotThreadSafe;
import org.pp.java8.util.ReflectionUtl;

import java.lang.reflect.Array;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * 通过有界队列演示Condition操作，当队列为空时不可以取元素，满时不可以放元素
 */
public class ConditionTest {

    private static final int N = 10;

    public static void main(String[] args) {
        BoundQueue<Integer> queue = new BoundQueue<>(N);
        // 线程交互，抢占cpu执行
        Stream.of(new ThreadA(queue), new ThreadA(queue), new ThreadA(queue)).parallel().forEach(t -> t.start());
        Stream.of(new ThreadB(queue), new ThreadB(queue), new ThreadB(queue)).parallel().forEach(t -> t.start());
    }

    @NotThreadSafe
    static final class BoundQueue<T> {
        private final T[] arr;
        private final int capacity;
        private volatile int size = 0; // 队列元素个数
        /* 可重入独占锁--一个线程持有锁，其他请求锁的线程等待BLOCKED状态 */
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        @NotThreadSafe
        public BoundQueue(T[] arr) {
            this.arr = arr;
            capacity = arr.length;
        }

        /**
         * 非线程安全的构造
         *
         * @param capacity 队列最大容量
         */
        @NotThreadSafe
        public BoundQueue(int capacity) {
            Class<T> clazz = ReflectionUtl.getSuperClassGenericType(getClass(), 0);
            arr = (T[]) Array.newInstance(clazz, capacity);
            this.capacity = capacity;
        }

        /**
         * 队列是否为空，空表示没有元素
         *
         * @return true表示队列空
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * 队列是否已满
         *
         * @return true就是满
         */
        public boolean isFull() {
            return size >= arr.length;
        }

        /**
         * @param t
         */
        public void put(T t) throws InterruptedException {
            lock.lock();
            try {
                if (isFull()) {
                    condition.await();
                }
                arr[size++] = t; // not ThreadSafe
                System.out.println("put item : " + t);
                condition.signal();
            } finally {
                lock.unlock();
            }
        }

        public T take() throws InterruptedException {
            lock.lock();
            try {
                if (isEmpty()) {
                    condition.await();
                }
                T t = arr[--size];
                System.out.println("take item : " + t);
                condition.signal();
                return t;
            } finally {
                lock.unlock();
            }
        }
    }

    static class ThreadA extends Thread {
        private BoundQueue queue;
        private final int queueLen;
        private int count;

        public ThreadA(BoundQueue queue) {
            this.queue = queue;
            this.queueLen = queue.capacity;
        }

        @Override
        public void run() {
            try {
                while (count < queueLen) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    queue.put(count++);
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    static class ThreadB extends Thread {
        private BoundQueue queue;
        private int queueLen;
        private int count;

        public ThreadB(BoundQueue queue) {
            this.queue = queue;
            this.queueLen = queue.capacity;
        }

        @Override
        public void run() {
            try {
                while (count++ < queueLen) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    queue.take();
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName());
            }
        }
    }
}
