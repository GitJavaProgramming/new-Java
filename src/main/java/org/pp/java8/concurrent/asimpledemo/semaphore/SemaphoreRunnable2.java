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
public class SemaphoreRunnable2 implements Runnable {

    /**
     * 第几个线程
     */
    private final int index;

    private SharedResource sharedResource;

    public SemaphoreRunnable2(SharedResource sharedResource, int index) {
        this.sharedResource = sharedResource;
        this.index = index;
    }

    @Override
    public void run() {
        sharedResource.serialPrint(index);
    }
}
