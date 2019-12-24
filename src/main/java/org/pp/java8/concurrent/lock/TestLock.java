package org.pp.java8.concurrent.lock;

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
public class TestLock {

    private static final int N = 3;

    public static void main(String[] args) throws InterruptedException {

        FirstReentrantLock reentrantLock = new FirstReentrantLock(N);
        Thread[] threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            threads[i] = new Thread(new TakeTask(reentrantLock, i));
        }
        for (int i = 0; i < N; i++) {
            threads[i].start();
        }

        Thread.sleep(2000);

        reentrantLock.wakeUp();

//        ExecutorService service = Executors.newFixedThreadPool(N);
//        service.submit(reentrantLock);
//        service.shutdown();
    }
}
