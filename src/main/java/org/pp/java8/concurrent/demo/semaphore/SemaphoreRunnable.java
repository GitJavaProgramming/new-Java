package org.pp.java8.concurrent.demo.semaphore;

import java.util.concurrent.Semaphore;

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
public class SemaphoreRunnable implements Runnable {

    private final int threadNum;
    private final int printCount;
    /**
     * 第几个线程
     */
    private final int index;

    private static volatile boolean flag = true;
    private static volatile char[] chars;  //  chars = new char[printCount * str.length()]; System.arraycopy(charsTemp, 0, chars, 0, charsTemp.length);
    private static volatile Semaphore[] semaphores = null;
    private static volatile int strIndex = 0;

    public SemaphoreRunnable(int threadNum, int printCount, int index) {
        this.threadNum = threadNum;
        this.printCount = printCount;
        this.index = index;
        if (flag) {
            flag = false;
            generateCharArray();
            System.out.print("shared resource:");
            System.out.println(chars);
            initSemaphoreArray();
        }
    }

    private void generateCharArray() {
        String str = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
        String tempStr = "";
        for (int i = 0; i < printCount; i++) {
            tempStr += str;
        }
        chars = tempStr.toCharArray();
    }

    private void initSemaphoreArray() {
        semaphores = new Semaphore[threadNum];
        for (int i = 0; i < threadNum; i++) {
            semaphores[i] = new Semaphore(1);
            if (i != threadNum - 1) {
                try {
                    semaphores[i].acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        Semaphore lastSemaphore = (index == 0 ? semaphores[threadNum - 1] : semaphores[index - 1]);
        Semaphore currSemaphore = semaphores[index];
        while (strIndex < chars.length) {
            try {
                lastSemaphore.acquire();
                if (strIndex >= chars.length) {
                    for (int x = 0; x < semaphores.length; x++) {
                        semaphores[x].release();
                    }
                    break;
                }
                System.out.println(Thread.currentThread().getName() + "-" + chars[strIndex++]);
                currSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
