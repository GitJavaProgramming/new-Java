/**
 * 并发包--java.util.concurrent.locks
 * CAS自旋机制
 * AbstractQueuedSynchronizer 抽象队列同步器
 *   默认情况下，每个方法都抛出 UnsupportedOperationException。
 *   tryAcquire(int)
 *   tryRelease(int)
 *   tryAcquireShared(int)
 *   tryReleaseShared(int)
 *   isHeldExclusively()
 *   通过实现这些方法来使用AQS，其他方法都是final的，定义为final是为了确保行为相同。
 *   AQS节点构造：
 *         Node() {    // Used to establish initial head or SHARED marker
 *         }
 *         Node(Thread thread, Node mode) {     // Used by addWaiter
 *             this.nextWaiter = mode;
 *             this.thread = thread;
 *         }
 *         Node(Thread thread, int waitStatus) { // Used by Condition
 *             this.waitStatus = waitStatus;
 *             this.thread = thread;
 *         }
 *    独占式获取状态量
 *    public final void acquire(int arg) {
 *         if (!tryAcquire(arg) &&
 *             acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
 *             selfInterrupt();
 *     }
 *   在获取同步状态时，同步器维护这一个同步队列，并持有对头节点和尾节点的引用。获取状态失败的线程会被包装成节点加入到尾节
 *   点后面称为新的尾节点，在进入同步队列后开始自旋，停止自旋的条件就是前驱节点为头节点并且成功获取到同步状态。在释放同步
 *   状态时，同步器调用tryRelease方法释放同步状态，然后唤醒头节点的后继节点。
 *   共享式获取状态量
 *   同步器调用tryAcquireShared方法尝试获取同步状态，tryAcquireShared返回值是一个int类型，当返回值大于0时，表示能够获
 *   取到同步状态。因此同步队列里的节点结束自旋状态的条件就是tryAcquireShared返回值大于0。
 *----------------------------------------------------------------------------------------------------------------------
 * Lock 显式锁、重入锁、公平锁
 * ReentrantLock实现了Lock接口，内部提供一个AQS实现，AQS维护一个变量，这个变量的操作是原子操作，同时维护一个fifo队列用于管理
 * 获取同步状态失败的线程
 */
package org.pp.java8.concurrent.lock;
