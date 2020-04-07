package org.pp.java8.patterns.behavioral.strategy;

public interface IStrategy {
    void fetch();

    default IStrategy getInstance() throws IllegalAccessException, InstantiationException {
        return getClass().newInstance();
    }
}
