package org.pp.java8.concurrent.lock.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RealLock implements Lock {

    private final Sync sync;

    public RealLock() {
        sync = new NonfairSync();
    }

    public RealLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }

    /**************************************锁**************************************/


    @Override
    public void lock() {

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
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    /**************************************同步器**************************************/
    abstract static class Sync extends AQS {

    }
    static final class NonfairSync extends Sync {}
    static final class FairSync extends Sync {}
}
