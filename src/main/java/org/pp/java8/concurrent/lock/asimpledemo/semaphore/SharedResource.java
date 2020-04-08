package org.pp.java8.concurrent.lock.asimpledemo.semaphore;

import java.util.concurrent.Semaphore;

public class SharedResource {

    private final int threadNum;
    private final int printCount;

    private static volatile Semaphore[] semaphores = null; // 线程进入许可

    private static volatile char[] chars; // 操作的字符数组
    private static volatile int strIndex = 0; // 字符数组索引

    public SharedResource(int threadNum, int printCount) { // 限制线程获取资源的顺序
        this.threadNum = threadNum;
        this.printCount = printCount;
        generateCharArray();
        initSemaphoreArray();
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

    public void serialPrint(int index) {
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
