package org.pp.java8.concurrent.thread;

import java.util.concurrent.TimeUnit;

/**
 * 两个线程A，B
 * B等待A执行完再执行后续操作，主线程中断A
 */
public class ThreadJoinTest {

    /**
     * 一种可能的运行结果
     * B run and wait A finished.
     * A running here.
     * B interrupt -->> A join interrupted...
     * A sleep interrupted...
     * A is RUNNABLE
     * AAAAAAAA finished
     * a.state = BLOCKED
     * B running here.
     *
     * FAQ 为什么A的状态会出现BLOCKED
     *
     */
    public static void main(String[] args) throws InterruptedException {
        A a = new A();
        B b = new B(a);
        b.start();
        a.start();
        TimeUnit.SECONDS.sleep(1);
        a.interrupt();
        b.interrupt(); // 中断A.join操作，捕获异常处理
    }

    static class A extends Thread {
        @Override
        public void run() {
            System.out.println("A running here.");
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                System.out.println("A sleep interrupted...");
//                System.out.println("A is " + Thread.currentThread().getState());
            }
            System.out.println("AAAAAAAA finished");
        }
    }

    static class B extends Thread {

        private A a;

        public B(A a) {
            this.a = a;
        }

        @Override
        public void run() {
            System.out.println("B run and wait A finished.");
            try {
                a.join(); // 等A执行完
            } catch (InterruptedException e) {
                System.out.println("B interrupt -->> A join interrupted...");
//                a.interrupt();
                System.out.println("a.state = " + a.getState()); // 这里可能出现BLOCKED join是同步方法
            }
            System.out.println("B running here.");
        }
    }

}
