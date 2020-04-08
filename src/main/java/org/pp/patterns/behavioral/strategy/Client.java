package org.pp.patterns.behavioral.strategy;

/**
 * 选择不同策略，对象的选择
 */
public class Client {
    public static void main(String[] args) {
        IStrategy strategy =  new Fetch360Strategy();
        fetch(strategy);
        strategy =  new FetchSSCStrategy();
        fetch(strategy);
    }

    public static void fetch(IStrategy strategy) {
        strategy.fetch();
    }
}
