package org.pp.java8.concurrent.lock.simpleFairLock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.LockSupport;

/**
 * Java并发有两个很重要的概念：
 * 1. 锁
 * 2. 线程安全的同步块
 * Java中使用队列同步器对锁竞争控制可以实现乐观锁形式并发控制，其底层原理使用处理器CMPXCHG指令
 * 锁可以保证线程对资源的占用，并发线程的乱序执行仍需要进行协调
 * 使用线程安全的同步块在有其他状态量的影响下仍需要同步，以达到所有状态量的共同一致
 */
public abstract class AQS extends AbstractOwnableSynchronizer {

    private transient volatile Node head;
    private transient volatile Node tail;
    /**
     * 同步状态量
     */
    private volatile int state;

    /**
     * 双向链表 锁等待队列
     * 两种锁类型：独占锁、共享锁。决定了节点形式EXCLUSIVE = null、SHARED->new Node()
     */
    static final class Node {
        /**
         * 共享锁
         */
        static final Node SHARED = new Node();
        /**
         * 独占锁
         */
        static final Node EXCLUSIVE = null;

        static final int CANCELLED = 1;
        static final int SIGNAL = -1;
        static final int CONDITION = -2;
        static final int PROPAGATE = -3;

        volatile int waitStatus;

        volatile Node prev;
        volatile Node next;
        /**
         * 线程，在锁等待队列中
         */
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

    protected final  int getState() {
        return state;
    }

    protected final  void setState(int state) {
        this.state = state;
    }

    /**************************************按需实现自己的锁**************************************/
    /**
     * 尝试以独占模式获取锁，在获取锁时需要判断是否是独占模式
     * 该方法始终由执行获取的线程调用。如果此方法报告失败，则acquire方法可以将线程排队（如果尚未排队），直到其他某个线程释放释放该信号为止。
     */
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

    /**************************************状态量与队列的同步**************************************/
    public final void acquire(int arg) {
        if (!tryAcquire(arg)/*001.获取锁失败 执行线程入队*/ && acquireQueued(addWaiter(Node.EXCLUSIVE), arg)/*002.线程包装为独占模式节点插入链表并作为尾节点*/) {
            Thread.currentThread().interrupt(); // 如果获取锁失败并且入队成功就中断，中断会引发中断异常，在调用中断方法时捕获进行处理
        }
    }

    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        Node pred = tail;
        if (pred != null) { // 非空
            node.prev = pred;
            if (compareAndSetTail(pred, node)/*cas一定会成功，由处理器CMPXCHG指令保证*/) {
                pred.next = node;
                return node;
            }
        }
        enq(node);/*tail==null 链表为空*/
        return node;
    }

    private Node enq(final Node node) {
        for (; ; ) {
            Node t = tail;
            if (t == null) { // Must initialize
                if (compareAndSetHead(new Node())/*初始化，头尾节点指向新建的默认构造的节点*/)
                    tail = head;
            } else { // 插入节点并作为双向链表的尾节点
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t; // 退出循环
                }
            }
        }
    }

    /**
     * 003.以排他的不可中断模式获取已在队列中的线程。 用于条件等待方法以及获取。
     * 获取锁失败之后还不甘心，自身入队后还要获取一遍？？
     * */
    final boolean acquireQueued(final Node node/*独占模式节点*/, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) { // 自旋判断
                final Node p = node.predecessor()/*node.prev*/;
                if (p == head/*node.prev == head只有两个节点（第一个节点为初始化时构造的默认节点）时再次tryAcquire*/ && tryAcquire(arg)/*再次获取时成功了执行if块*/) {
                    setHead(node);
//                    setHead()方法如下：
//                    head = node; // 头节点指针指向当前节点
//                    node.thread = null;
//                    node.prev = null; // 初始化构造的默认节点置空
                    p.next = null; // help GC 帮助gc，p.next就是当前节点，获取锁之后就要删除掉，从前往后删
                    failed = false;
                    return interrupted; // 只有两个结点并且获取锁成功时return interrupted
                }
                if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()/*park this阻塞等待Thread.interrupted*/) /* 链表中不止两个节点排队时应该等待 */
                    interrupted = true; // 中断标志设置true，再次循环
            }
        } finally {
            if (failed) { // 成功获得锁时，退出循环，执行到finally
                cancelAcquire(node);
            }
        }
    }

    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;/*waitStatus int型默认为0*/
        if (ws == Node.SIGNAL)/*如果旧的尾节点状态为SIGNAL return true*/
            /*
             * This node has already set status asking a release
             * to signal it, so it can safely park.
             */
            return true;
        if (ws > 0/*旧的尾节点被cancelled CANCELLED状态在cancelAcquire()方法中设置*/) {
            /*
             * Predecessor was cancelled. Skip over predecessors and
             * indicate retry.
             */
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            /*
             * waitStatus must be 0 or PROPAGATE.  Indicate that we
             * need a signal, but don't park yet.  Caller will need to
             * retry to make sure it cannot acquire before parking.
             */
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);/*设置前一个结点的等待状态为SIGNAL，前一个结点就是旧的尾节点*/
        }
        return false;
    }

    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    private void cancelAcquire(Node node) {
        // Ignore if node doesn't exist
        if (node == null)
            return;

        node.thread = null;

        // Skip cancelled predecessors
        Node pred = node.prev;
        while (pred.waitStatus > 0)
            node.prev = pred = pred.prev;

        // predNext is the apparent node to unsplice. CASes below will
        // fail if not, in which case, we lost race vs another cancel
        // or signal, so no further action is necessary.
        Node predNext = pred.next;

        // Can use unconditional write instead of CAS here.
        // After this atomic step, other Nodes can skip past us.
        // Before, we are free of interference from other threads.
        node.waitStatus = Node.CANCELLED;

        // If we are the tail, remove ourselves.
        if (node == tail && compareAndSetTail(node, pred)) {
            compareAndSetNext(pred, predNext, null);
        } else {
            // If successor needs signal, try to set pred's next-link
            // so it will get one. Otherwise wake it up to propagate.
            int ws;
            if (pred != head &&
                    ((ws = pred.waitStatus) == Node.SIGNAL ||
                            (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
                    pred.thread != null) {
                Node next = node.next;
                if (next != null && next.waitStatus <= 0)
                    compareAndSetNext(pred, predNext, next);
            } else {
                unparkSuccessor(node);
            }

            node.next = node; // help GC
        }
    }

    private void unparkSuccessor(Node node) {
        /*
         * If status is negative (i.e., possibly needing signal) try
         * to clear in anticipation of signalling.  It is OK if this
         * fails or if status is changed by waiting thread.
         */
        int ws = node.waitStatus;
        if (ws < 0)
            compareAndSetWaitStatus(node, ws, 0);

        /*
         * Thread to unpark is held in successor, which is normally
         * just the next node.  But if cancelled or apparently null,
         * traverse backwards from tail to find the actual
         * non-cancelled successor.
         */
        Node s = node.next;
        if (s == null || s.waitStatus > 0) {
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
        if (s != null)
            LockSupport.unpark(s.thread);
    }

    /**
     * 队列（双向链表）中是否在排队
     * 1. 队列为空 head==tail
     * 2. 队列只有一个node见enq()方法 tail = head= new Node() head==tail
     * 3. 2个或者以上 ((s = h.next) == null || s.thread != Thread.currentThread())
     *  (s = h.next) == null 这里一定为假吧？ 到了第3步头节点.next会等于null??除非还有并发修改队列取出链表中node的线程？？
     *  后面接着判断不是自身线程说明不是重入？？
     *
     */
    public final boolean hasQueuedPredecessors() {
        // The correctness of this depends on head being initialized
        // before tail and on head.next being accurate if the current
        // thread is first in queue.
        Node t = tail; // Read fields in reverse initialization order
        Node h = head;
        Node s/*辅助指针，临时*/;
        return h != t && ((s = h.next) == null || s.thread != Thread.currentThread());
    }

    public final boolean release(int arg) {
        if (tryRelease(arg)) {
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);
            return true;
        }
        return false;
    }


    /*******************************************原子操作帮助********************************************/
    private static final Unsafe unsafe = getUnsafe();
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

    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    private static final boolean compareAndSetWaitStatus(Node node, int expect, int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset, expect, update);
    }

    private static final boolean compareAndSetNext(Node node, Node expect, Node update) {
        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
    }

    /**
     * 同步状态，以cas方式设置值
     */
    protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    public static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe)f.get(null);
        } catch (Exception e) {
            /* ... */
        }
        return null;
    }
}
