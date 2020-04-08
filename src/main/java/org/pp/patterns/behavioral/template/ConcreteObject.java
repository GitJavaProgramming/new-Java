package org.pp.patterns.behavioral.template;

public class ConcreteObject extends AbstractTemplate {

    @Override
    public void templateMethod() {
        System.out.println("object");
    }
}
