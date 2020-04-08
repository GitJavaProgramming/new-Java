package org.pp.patterns.behavioral.chain;

@FunctionalInterface
public interface ChainHandler {

    //    <V> CompleteFuture<V> filter();
    boolean filter();

    default boolean isDone() {
        return false;
    }
}
