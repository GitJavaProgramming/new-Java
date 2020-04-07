package org.pp.java8.patterns.behavioral.chain;

import java.util.Arrays;

/**
 * 外部行为传递
 */
public class SelfAssociationChainHandler<E> implements ChainHandler {

    private ChainHandler[] handler;
    private boolean done = true;

    public SelfAssociationChainHandler(ChainHandler... handler) {
        this.handler = handler;
        if (this.handler == null || this.handler.length == 0) {
            this.handler = new ChainHandler[]{
                    () -> {
                        System.out.println("handler1");
                        return true;
                    },
                    () -> false
            };
        }
    }

    @Override
    public boolean filter() {
        Arrays.stream(handler).parallel().forEach(h -> {
            done &= h.filter();
            if (!done) {
//                log
                return;
            }
        });
        return done;
    }

    public boolean isDone() {
        return done;
    }
}
