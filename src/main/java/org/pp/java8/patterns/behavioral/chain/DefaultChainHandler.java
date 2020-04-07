package org.pp.java8.patterns.behavioral.chain;

/**
 * 不做自关联的传递，维护内部行为传递链表
 */
public class DefaultChainHandler {

    // 维护双向链
    private ChainNode head;
    private ChainNode tail;

    public DefaultChainHandler() {
    }

    class ChainNode<E> {
        private ChainNode<E> prev;
        private ChainNode<E> next;
        private final String nodeName;
        private E data;

        public ChainNode(String nodeName) {
            this.nodeName = nodeName;
        }

        public ChainNode(String nodeName, E data) {
            this.nodeName = nodeName;
            this.data = data;
        }

        public void filter() {
            System.out.println(nodeName + " filter");
            while (next != null) { // 没到尾节点
                next.filter();
                return;
            }
        }
    }

    /**
     * 放入节点
     */
    protected void offer(ChainNode chainNode) {
        ChainNode node = tail;
        if (node == null) { // 初始化链表
            node = new ChainNode("head");
            head = node;
            tail = head;
//            return; // 必须放入队列
        }
        chainNode.prev = node;
        node.next = chainNode;
        tail = chainNode;
    }

    public <E> void addMessage(String msg, E... data) {
        ChainNode node1 = new ChainNode(msg);
        offer(node1);
    }

    public void filterInChain() {
        head.filter(); // 传递
    }
}
