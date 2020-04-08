package org.pp.patterns.structural.bridge;

public class ConcreteImplementor extends DefaultImplementor {

    @Override
    public void doActionImpl() {
        System.out.println("ConcreteImplementor");
    }
}
