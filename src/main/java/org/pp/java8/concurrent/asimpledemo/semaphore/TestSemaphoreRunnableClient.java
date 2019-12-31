package org.pp.java8.concurrent.asimpledemo.semaphore;

/**
 * N个线程，打印AaBb...Zz，M遍
 * 0-A
 * 1-a
 * 2-B
 * ...
 * 0-Y
 * 1-y
 * 2-Z
 * 0-z
 * 0-A  ?  1-A  ?
 * 1-a  ?  2-a  ?
 */
public class TestSemaphoreRunnableClient {

    private static final int N = 10;
    private static final int M = 2;

    public static void main(String[] args) {
        SharedResource resource = new SharedResource(N, M);
        Thread[] threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            threads[i] = new Thread(new SemaphoreRunnable2(resource, i));
        }
        for (int i = 0; i < N; i++) {
            threads[i].start();
        }
    }
}
