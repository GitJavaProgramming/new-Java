package org.pp.patterns.structural.bridge;

public abstract class Abstraction implements Action {

    /** 行为实现器，需要注入，见 NewConcreteAction */
    protected Implementor implementor;

    @Override
    public void doAction() {
        implementor.doActionImpl(); // default impl
    }
}
