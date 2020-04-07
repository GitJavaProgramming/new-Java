package org.pp.java8.patterns.behavioral.template;

public abstract class AbstractTemplate implements ITemplate, ICallEntry {

    public void defaultMethod() {
        templateMethod();
    }
}
