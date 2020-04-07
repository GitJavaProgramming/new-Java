package org.pp.java8.patterns.structural.bridge;

public class NewConcreteImplementor extends DefaultImplementor {

    @Override
    public void doActionImpl() {
        System.out.println("NewConcreteImplementor");
    }
}
