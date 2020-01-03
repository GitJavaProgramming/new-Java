/**
 * CopyOnWrite
 * -- 因为CopyOnWrite的写时复制机制，所以在进行写操作的时候，内存里会同时驻扎两个对象的内存，旧的对象和新写入的对象。
 * 如果这些对象占用的内存比较大，有可能造成频繁的Yong GC和Full GC。
 * -- CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。所以如果你希望写入的的数据，马上能读到，请不要使用CopyOnWrite容器。
 * HashMap的非线程安全性与ConcurrentHashMap的线程安全性
 * ---- HashMap的状态量
 * ---- ConcurrentHashMap的状态量与同步控制
 */
package org.pp.java8.concurrent.util;