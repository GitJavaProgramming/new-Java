package org.pp.patterns.behavioral.strategy;

public class FetchSSCStrategy implements IStrategy {

    @Override
    public void fetch() {
        System.out.println("ssc");
    }
}
