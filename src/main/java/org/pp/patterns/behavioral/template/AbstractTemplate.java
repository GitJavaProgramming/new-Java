package org.pp.patterns.behavioral.template;

public abstract class AbstractTemplate implements ITemplate, ICallEntry {

    public void defaultMethod() {
        templateMethod();
    }
}
