package org.pp.java8.patterns.structural.bridge;

public class ConcreteImplementor extends DefaultImplementor {

    @Override
    public void doActionImpl() {
        System.out.println("ConcreteImplementor");
    }
}
