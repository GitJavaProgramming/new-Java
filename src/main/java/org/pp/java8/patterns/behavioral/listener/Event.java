package org.pp.java8.patterns.behavioral.listener;

public class Event<S> implements java.io.Serializable {

    protected transient S source;

    public Event(S source) {
        this.source = source;
    }

    public S getSource() {
        return source;
    }
}
