package org.pp.patterns.structural.bridge;

public class ConcreteAction extends Abstraction {

    @Override
    public void doAction() {
        System.out.println("ConcreteAction");
    }
}
