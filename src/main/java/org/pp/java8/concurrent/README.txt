Java中的线程
查看UML类图结构

线程状态
Thread.State---查阅源码
NEW
至今尚未启动的线程处于这种状态。
RUNNABLE
正在 Java 虚拟机中执行的线程处于这种状态。
可运行线程的线程状态。处于可运行状态的某一线程正在 Java 虚拟机中运行，但它可能正在等待操作系统中的其他资源，比如处理器。
BLOCKED
受阻塞并等待某个监视器锁的线程处于这种状态。
受阻塞并且正在等待监视器锁的某一线程的线程状态。处于受阻塞状态的某一线程正在等待监视器锁，以便进入一个同步的块/方法，或者在
调用 Object.wait 之后再次进入同步的块/方法。
WAITING
无限期地等待另一个线程来执行某一特定操作的线程处于这种状态。
某一等待线程的线程状态。某一线程因为调用下列方法之一而处于等待状态：
    不带超时值的 Object.wait
    不带超时值的 Thread.join
    LockSupport.park
处于等待状态的线程正等待另一个线程，以执行特定操作。 例如，已经在某一对象上调用了 Object.wait() 的线程正等待另一个线程，以便
在该对象上调用 Object.notify() 或 Object.notifyAll()。已经调用了 Thread.join() 的线程正在等待指定线程终止。
TIMED_WAITING
等待另一个线程来执行取决于指定等待时间的操作的线程处于这种状态。
具有指定等待时间的某一等待线程的线程状态。某一线程因为调用以下带有指定正等待时间的方法之一而处于定时等待状态：
    Thread.sleep
    带有超时值的 Object.wait
    带有超时值的 Thread.join
    LockSupport.parkNanos
    LockSupport.parkUntil
TERMINATED
已退出的线程处于这种状态。















参考资料
多线程基础知识，按个人阅读获益程度排序
JAVA并发编程实践.pdf
Java高并发编程详解：多线程与架构设计
Java并发编程之美.pdf
Java多线程编程实战指南  核心篇.pdf
并发编程
实战Java高并发程序设计（第2版）.pdf
Java多线程编程实战指南 设计模式篇.pdf
Java多线程设计模式.pdf
Java虚拟机并发编程-中文完整版.pdf
Java并发编程_设计原则与模式(第二版)  比较老.pdf

















