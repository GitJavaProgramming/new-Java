package org.pp.patterns.structural.proxy;

public class ThreadProxy extends Thread implements Runnable {

    protected String threadName;

    public ThreadProxy(String threadName) {
        this.threadName = "ThreadProxy-" + threadName;
    }

    @Override
    public void run() {
        preProcess();
        postProcess();
    }

    private void preProcess() {
        System.out.println("preProcess");
    }
    private void postProcess() {
        System.out.println("postProcess");
    }

    protected ThreadProxy printSelfName() {
        System.out.println(threadName);
        return this;
    }
}
