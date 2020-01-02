/**
 * 并发包--java.util.concurrent.locks
 * CAS自旋机制
 * AbstractQueuedSynchronizer 抽象队列同步器
 * Lock 显式锁、重入锁、公平锁
 * ReentrantLock实现了Lock接口，内部提供一个AQS实现，AQS维护一个变量，这个变量的操作是原子操作，同时维护一个fifo队列用于管理
 * 获取同步状态失败的线程
 */
package org.pp.java8.concurrent.lock;
