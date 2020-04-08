package org.pp.java8.concurrent.lock.simpleFairLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class FairLock implements Lock {

    private final Sync sync;

    public FairLock() {
//        sync = new NonfairSync();
        this(true);
    }

    public FairLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }

    /**************************************锁**************************************/
    @Override
    public void lock() {
        sync.lock();
    }

    public void unlock() {
        sync.release(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    /**************************************同步器**************************************/
    abstract static class Sync extends AQS {

        abstract void lock();

        protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
    }

    static final class FairSync extends Sync {

        final void lock() {
            acquire(1);
        }

        /**
         * 当前线程尝试获取锁，使用状态量和队列控制
         */
        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) { // 可以获取锁
                if (!hasQueuedPredecessors() && compareAndSetState(0, acquires)/*队列为空或者只有一个node时当前线程设置状态量*/) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()/*当前线程已经获得独占锁*/) {
                int nextc = c + acquires;
                if (nextc < 0) /*这里还会<0？？*/
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;/*线程获取锁失败*/
        }
    }

    static final class NonfairSync extends Sync {
        @Override
        void lock() {

        }
    }
}
