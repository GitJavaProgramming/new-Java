/**
 * 目录
 * Java中的线程与OS线程
 * Java编译器与解释执行系统
 * java.lang.Thread
 * --  属性：id name daemon priority
 * --  Java线程状态：Thread.State
 * --  线程API：sleep、join、yield、interrupt
 * --  任务关闭与取消
 * 线程组与线程池
 * -- 线程管理
 * -- Java Executor框架
 * -- 异步任务
 * Java内存模型
 * -- 原子性、可见性、有序性
 * -- happens-before 先后发生原则
 * -- CAS 乐观锁概念
 * -- volatile、synchronized、final在Java内存模型的语义
 * -- 顺序一致性理想模型、Java编译器优化与指令重排序
 * Java高并发---实现原理与工具类使用
 * -- 线程安全（不需要额外同步或其他处理，多线程下行为（构造方法、实例方法、类方法？）仍一致）文档化
 * -- AQS
 * ---- 基本原理（volatile状态量与同步队列）
 * ---- Java中的队列同步器的应用（CountDownLatch、CyclicBarrier、Semaphore、Exchanger）
 * -- atomic包中原子操作类----基本类型与引用类型（CAS应用）
 * -- 线程安全的集合框架(HashMap的非线程安全性与ConcurrentHashMap的线程安全性源码剖析)
 * -- 锁
 * 活跃度、性能与测试
 *
 * 参考资料与进阶读物
 * -- Java并发编程实践（线程安全的本质与应用）
 * -- Java并发编程的艺术（Java中并发知识讲的比较透彻）
 * -- Java多线程实战指南 设计模式篇（还好，当线程安全设计专题看看吧）
 * -- 实战Java高并发程序设计（进阶专题）
 *
 */
package org.pp.java8.concurrent;