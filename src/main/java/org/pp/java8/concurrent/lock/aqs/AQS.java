package org.pp.java8.concurrent.lock.aqs;

import sun.misc.Unsafe;

/**
 * Java并发有两个很重要的概念：
 * 1. 锁
 * 2. 线程安全的同步块
 * Java中使用队列同步器对锁竞争控制可以实现乐观锁形式并发控制，其底层原理使用处理器CMPXCHG指令
 * 锁可以保证线程对资源的占用，并发线程的乱序执行仍需要进行协调
 * 使用线程安全的同步块在有其他状态量的影响下仍需要同步，以达到所有状态量的共同一致
 */
public abstract class AQS {

    private transient volatile Node head;
    private transient volatile Node tail;
    /** 同步状态量 */
    private volatile int state;

    /**
     * 双向链表 锁等待队列
     * 两种锁类型：独占锁、共享锁。决定了节点形式EXCLUSIVE = null、SHARED->new Node()
     */
    static final class Node {
        /** 共享锁 */
        static final Node SHARED = new Node();
        /** 独占锁 */
        static final Node EXCLUSIVE = null;

        static final int CANCELLED = 1;
        static final int SIGNAL = -1;
        static final int CONDITION = -2;
        static final int PROPAGATE = -3;

        volatile int waitStatus;

        volatile Node prev;
        volatile Node next;
        /** 线程，在锁等待队列中 */
        volatile Thread thread;

        Node nextWaiter;

        final boolean isShared() {
            return nextWaiter == SHARED;
        }

        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }

        Node() {    // Used to establish initial head or SHARED marker
        }

        Node(Thread thread, Node mode) {     // Used by addWaiter
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus) { // Used by Condition
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
    }

    /**************************************按需实现自己的锁**************************************/
    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }
    protected boolean tryRelease(int arg) {
        throw new UnsupportedOperationException();
    }
    protected int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }
    protected boolean tryReleaseShared(int arg) {
        throw new UnsupportedOperationException();
    }
    protected boolean isHeldExclusively() {
        throw new UnsupportedOperationException();
    }

    /*************************************原子操作帮助**************************************/
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(AQS.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset(AQS.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset(AQS.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("next"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }
}
