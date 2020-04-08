package org.pp.patterns.behavioral.chain;

/**
 * 责任链，行为传递
 */
public class Client {
    public static void main(String[] args) {
//        DefaultChainHandler chain = new DefaultChainHandler();
//        chain.addMessage("node1");
//        chain.addMessage("node2");
//        chain.filterInChain();


        ChainHandler handler = new SelfAssociationChainHandler(); // 可以全部做异步处理
        handler.filter();
        System.out.println(handler.isDone());
    }
}
