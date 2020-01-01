package org.pp.java8.concurrent.lock.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Stream;

/**
 * CyclicBarrier
 * 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。在涉及一组固定大小的线程的程序中，
 * 这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。
 * CyclicBarrier 支持一个可选的 Runnable 命令，在一组线程中的最后一个线程到达之后（但在释放所有线程之前），该命令只在每个屏障
 * 点运行一次。若在继续所有参与线程之前更新共享状态，此屏障操作很有用。
 */
public class CyclicBarrierTest {

    private static final int N = 3;

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(N);
        MyTask shared = new MyTask(barrier);
        Stream.of(new Thread(shared), new Thread(shared), new Thread(shared))
                .parallel()
                .forEach(Thread::start);
    }

    static class MyTask implements Runnable {

        private final CyclicBarrier barrier;

        public MyTask(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "wait.");
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "run here.");
        }
    }
}
