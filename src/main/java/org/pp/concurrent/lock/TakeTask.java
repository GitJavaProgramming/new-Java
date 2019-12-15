package org.pp.concurrent.lock;

public class TakeTask implements Runnable {

    private FirstReentrantLock firstReentrantLock;
    private final int name;

    public TakeTask(FirstReentrantLock firstReentrantLock, int name) {
        this.firstReentrantLock = firstReentrantLock;
        this.name = name;
    }

    public void run() {
        Thread.currentThread().setName(name + "");
        firstReentrantLock.waitLock();
    }
}
