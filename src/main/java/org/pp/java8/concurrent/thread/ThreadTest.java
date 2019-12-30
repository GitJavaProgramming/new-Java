package org.pp.java8.concurrent.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程基础知识测试
 */
public class ThreadTest {
    public static void main(String[] args) {
//        System.out.println(ThreadTest.class.getResource("")); // 当前类在文件系统中的绝对路径
//        printThreadInfo(Thread.currentThread());
//        threadFieldTest();

//        threadMethodTest();
//        System.out.println(Thread.currentThread().getName() + " run here.");
    }

    /**
     * 测试当前线程
     *      线程属性  id name daemon priority等
     *      线程状态 Thread.State
     * 查阅Thread UML类图
     */
    public static void threadFieldTest() {
        Thread thread = new Thread(() -> {
            Thread currThread = Thread.currentThread();
            printThreadInfo(currThread);
        });
        System.out.println("Thread instantiate State :" + thread.getState());
        thread.setDaemon(true); // before start setter，这里设置为true时不打印线程信息 JDK1.8.0_221测试
        thread.start();
    }

    /**
     * 打印线程信息
     *
     * @param thread 要打印信息的线程
     */
    private static void printThreadInfo(Thread thread) {
        String name = thread.getName(); // name
        long id = thread.getId(); // id
        Thread.State state = thread.getState(); // thread state
        int priority = thread.getPriority(); // priority
        boolean daemon = thread.isDaemon(); // daemon

        ThreadGroup threadGroup = thread.getThreadGroup();
        threadGroup.list(); // 查阅toString list() 源码--调用Thread.toString 输出到控制台

        List<Object> fieldList = new ArrayList<>(); // Object类型参数 列表 忽视泛型
        fieldList.add(name);
        fieldList.add(id);
        fieldList.add(state);
        fieldList.add(priority);
        fieldList.add(daemon);
        System.out.println(fieldList);
        System.out.println("**************************************************************");
    }

    /**
     * 线程api
     *  sleep join yield interrupt
     */
    public static void threadMethodTest() {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " sleep.");
            try {
                Thread.sleep(1000); // sleep 单位-毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /**
             * 给调度程序的提示是当前线程愿意放弃当前使用的处理器。 调度程序可以随意忽略此提示。
             * public static native void yield();
             * */
            Thread/*.currentThread()*/.yield(); // 让步失效
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Finished sleep.");
        });

        thread.start();
        try {
            thread.join(); // Waits for this thread to die.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
