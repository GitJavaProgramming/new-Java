package org.pp.java8.concurrent.asimpledemo.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ConditionNode {

    private final Lock lock;
    private final Condition condition;

    public ConditionNode(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    public Lock getLock() {
        return lock;
    }

    public Condition getCondition() {
        return condition;
    }
}
