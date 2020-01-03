package org.pp.java8.concurrent.util;

import java.util.Arrays;
import java.util.HashMap;

/**
 * N个线程对同一个HashMap put
 */
public class HashMapNotThreadSafeTest {

    private static final int N_THREAD = 10;

    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>(2);
        Task task = new Task(map);
        Thread[] threads = new Thread[N_THREAD];
        for (int i = 0; i < N_THREAD; i++) {
            threads[i] = new Thread(task);
        }
        Arrays.stream(threads).forEach(Thread::start);
        System.out.println(map);
    }

    static class Task implements Runnable {

        private static int count;

        private final HashMap<Integer, Integer> map;

        Task(HashMap<Integer, Integer> map) {
            this.map = map;
        }


        @Override
        public void run() {
            map.put(++count, count);
        }
    }
}
