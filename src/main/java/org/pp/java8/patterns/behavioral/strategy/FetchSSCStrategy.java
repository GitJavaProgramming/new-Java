package org.pp.java8.patterns.behavioral.strategy;

public class FetchSSCStrategy implements IStrategy {

    @Override
    public void fetch() {
        System.out.println("ssc");
    }
}
