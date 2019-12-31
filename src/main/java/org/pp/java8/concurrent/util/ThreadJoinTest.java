package org.pp.java8.concurrent.util;

import java.util.concurrent.TimeUnit;

/**
 * 两个线程A，B，B等待A执行完再执行后续操作，主线程中断A
 * 一种可能的结果（1，2语句顺序可能不一样）：
 * 1 B run and wait A finished.
 * 2 A running here.
 * B interrupt -->> A join interrupted...
 * B running here.
 * AAAAAAAA finished
 */
public class ThreadJoinTest {
    public static void main(String[] args) throws InterruptedException {
        A a = new A();
        B b = new B(a);
        a.start();
        b.start();
        TimeUnit.SECONDS.sleep(1);
//        a.interrupt();
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
            }
            System.out.println("B running here.");
        }
    }

}
