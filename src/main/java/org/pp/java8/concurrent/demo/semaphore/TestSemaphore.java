package org.pp.java8.concurrent.demo.semaphore;

public class TestSemaphore {
    private static final int N = 3;
    private static final int M = 2;

    public static void main(String[] args) {
        Thread[] threads = new Thread[N];
        Runnable[] runnableArray = new Runnable[N];
        for (int i = 0; i < N; i++) {
            runnableArray[i] = new SemaphoreRunnable(N, M, i);
            threads[i] = new Thread(runnableArray[i]);
        }
        for (int i = 0; i < N; i++) {
            threads[i].start();
        }
    }
}
