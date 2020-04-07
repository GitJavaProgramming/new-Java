package org.pp.java8.patterns.behavioral.template;

public class ConcreteInstance extends AbstractTemplate {

    @Override
    public void templateMethod() {
        System.out.println("instance");
    }
}
